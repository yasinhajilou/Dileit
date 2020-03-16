package com.example.dileit.view.fragment.wordinfo.leitnersetup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.dileit.R;
import com.example.dileit.view.adapter.viewpager.AddNewLeitnerViewPagerAdapter;
import com.example.dileit.viewmodel.SharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

public class AddNewLeitnerDialog extends BottomSheetDialogFragment {
    private final String TAG = AddNewLeitnerDialog.class.getSimpleName();
    private SharedViewModel mSharedViewModel;
    private String title;
    private TextView tvTitle;
    private EditText edtGuide;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private AddNewLeitnerViewPagerAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_new_item_leitner , container,false);
        mAdapter = new AddNewLeitnerViewPagerAdapter(getChildFragmentManager());
        tvTitle = view.findViewById(R.id.tv_title_dialog_add_leitner);
        edtGuide = view.findViewById(R.id.edt_dialog_guide);
        mPager = view.findViewById(R.id.view_pager_add_new_leitner);
        tvTitle.setText(title);
        mPager.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);
    }
}
