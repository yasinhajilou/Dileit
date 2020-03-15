package com.example.dileit.view.fragment.wordinfo.leitnersetup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.dileit.R;
import com.example.dileit.viewmodel.SharedViewModel;

public class AddNewLeitnerDialog extends DialogFragment {
    private final String TAG = AddNewLeitnerDialog.class.getSimpleName();
    private SharedViewModel mSharedViewModel;
    private String title;
    private TextView tvTitle;
    private EditText edtGuide;
    private ViewPager mPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mSharedViewModel.getLeitnerItemData().observe(getActivity(), strings -> {
            title = strings[0];
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_new_item_leitner, null);
        builder.setView(view);
        tvTitle = view.findViewById(R.id.tv_title_dialog_add_leitner);
        edtGuide = view.findViewById(R.id.edt_dialog_guide);
        mPager = view.findViewById(R.id.view_pager_add_new_leitner);

        tvTitle.setText(title);

        return builder.create();
    }
}
