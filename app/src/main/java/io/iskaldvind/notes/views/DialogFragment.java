package io.iskaldvind.notes.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.iskaldvind.notes.R;
import io.iskaldvind.notes.ui.OnDeleteNoteDialogListener;


public class DialogFragment extends BottomSheetDialogFragment {
    
    private OnDeleteNoteDialogListener mOnDeleteNoteDialogListener;

    public DialogFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.dialog_delete, container, false);
        setCancelable(false);

        MainActivity parent = (MainActivity) requireActivity();
        mOnDeleteNoteDialogListener = parent.onDeleteNoteDialogListener;

        view.findViewById(R.id.dialog_delete_no_button).setOnClickListener(view1 -> {
            dismiss();
            mOnDeleteNoteDialogListener.onDialogNegative();
        });

        view.findViewById(R.id.dialog_delete_yes_button).setOnClickListener(view12 -> {
            dismiss();
            mOnDeleteNoteDialogListener.onDialogPositive();
        });

        return view;
    }
}