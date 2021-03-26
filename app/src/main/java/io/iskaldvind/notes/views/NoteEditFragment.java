package io.iskaldvind.notes.views;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.data.CardDataSource;
import io.iskaldvind.notes.data.CardDataSourceFirebaseImpl;
import io.iskaldvind.notes.data.CardDataSourceImpl;
import io.iskaldvind.notes.models.CardData;


public class NoteEditFragment extends Fragment {
    private static final String ARG_ITEM_IDX = "NoteEditFragment.item_idx";
    private static final String ARG_IS_NEW = "NoteEditFragment.is_new";

    private int mCurrentItemIdx = -1;
    private boolean mIsNew = false;

    public NoteEditFragment() {
        // Required empty public constructor
    }
    
    public static NoteEditFragment newInstance(int currentItemIdx, boolean isNew) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_IDX, currentItemIdx);
        args.putBoolean(ARG_IS_NEW, isNew);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCurrentItemIdx = getArguments().getInt(ARG_ITEM_IDX, -1);
            mIsNew = getArguments().getBoolean(ARG_IS_NEW, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);

        final CardDataSourceFirebaseImpl dataSource = CardDataSourceFirebaseImpl.getInstance();
        
        CardData cardData;
        if (mIsNew || dataSource.getItemsCount() < mCurrentItemIdx) {
            cardData = new CardData("", "", "", "", new Date().toString());
        } else {
            cardData = dataSource.getItemAt(mCurrentItemIdx);
        }

        final TextInputEditText editPhoto = view.findViewById(R.id.note_edit_photo_field);
        final TextInputEditText editUrl = view.findViewById(R.id.note_edit_url_field);
        final TextInputEditText editTitle = view.findViewById(R.id.note_edit_title_field);
        final TextInputEditText editDescription = view.findViewById(R.id.note_edit_description_field);
        
        if (!mIsNew) {
            if (cardData.getPhoto().length() > 0) editPhoto.setText(cardData.getPhoto());
            if (cardData.getUrl().length() > 0) editUrl.setText(cardData.getUrl());
            if (cardData.getTitle().length() > 0) editTitle.setText(cardData.getTitle());
            if (cardData.getDescription().length() > 0) editDescription.setText(cardData.getDescription());
        }
        
        final MaterialButton btnSave = view.findViewById(R.id.note_edit_save);
        btnSave.setOnClickListener((v) -> {
            cardData.setPhoto(editPhoto.getText().toString());
            cardData.setUrl(editUrl.getText().toString());
            cardData.setTitle(editTitle.getText().toString());
            cardData.setDescription(editDescription.getText().toString());
            Log.d("EDIT", cardData.toString());
            if (mIsNew) {
                dataSource.add(cardData);
            } else {
                dataSource.update(cardData);
            }
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        final MaterialButton btnCancel = view.findViewById(R.id.note_edit_cancel);
        btnCancel.setOnClickListener((v) -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });
        
        return view;
    }
}