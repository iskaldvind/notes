package io.iskaldvind.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesListFragment extends Fragment {
    
    private int mCurrentNoteIdx = -1;
    
    public NotesListFragment() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle != null) {
            mCurrentNoteIdx = bundle.getInt(NoteFragment.KEY_NOTE_INDEX, -1);
            if (mCurrentNoteIdx != -1 && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                showInSideContainer(mCurrentNoteIdx);
            }
        }
        initList(view);
    }
    
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] titles = getResources().getStringArray(R.array.titles);
        for (int index = 0; index < titles.length; index++) {
            TextView tvTitle = new TextView(getContext());
            String title = titles[index];
            tvTitle.setText(title);
            tvTitle.setTextSize(24);
            final int idx = index;
            tvTitle.setOnClickListener((it) -> {
                setCurrentIdx(idx);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    openAsSeparateActivity(idx);
                } else {
                    showInSideContainer(idx);
                }
            });
            layoutView.addView(tvTitle);
        }
    }
    
    private void openAsSeparateActivity (int index) {
        Intent intent = new Intent(getActivity(), NoteActivity.class);
        intent.putExtra(NoteActivity.KEY_NOTE_INDEX, index);
        startActivity(intent);
    }

    private void showInSideContainer (int index) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_container, NoteFragment.newInstance(index));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
    
    private void setCurrentIdx(int index) {
        mCurrentNoteIdx = index;
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(NoteFragment.KEY_NOTE_INDEX, mCurrentNoteIdx);
    }
}
