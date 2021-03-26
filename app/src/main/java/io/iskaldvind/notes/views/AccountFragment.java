package io.iskaldvind.notes.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.iskaldvind.notes.R;


public class AccountFragment extends Fragment {
    
    final static String KEY_OPTION_1 = "AccountFragment.option_1";
    
    public AccountFragment() {}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CircleImageView avatar = view.findViewById(R.id.account_avatar);
        ((MainActivity) Objects.requireNonNull(getActivity())).imageLoader.displayImage(getString(R.string.user_avatar_url), avatar);
        TextView name = view.findViewById(R.id.account_name);
        name.setText(getString(R.string.user_name));
        MaterialCheckBox option1CheckBox = view.findViewById(R.id.account_option_box_1);
        option1CheckBox.setChecked(((MainActivity) getActivity()).settingOption1);
        option1CheckBox.setOnCheckedChangeListener((it, isChecked) -> {
            ((MainActivity) getActivity()).settingOption1 = isChecked;
        });
    }
}
