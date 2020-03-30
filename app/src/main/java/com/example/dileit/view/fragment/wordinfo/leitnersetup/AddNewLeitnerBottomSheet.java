package com.example.dileit.view.fragment.wordinfo.leitnersetup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.viewpager.AddNewLeitnerViewPagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.SharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

public class AddNewLeitnerBottomSheet extends BottomSheetDialogFragment {
    private final String TAG = AddNewLeitnerBottomSheet.class.getSimpleName();
    private SharedViewModel mSharedViewModel;
    private String title;
    private TextView tvTitle;
    private EditText edtGuide;
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private AddNewLeitnerViewPagerAdapter mAdapter;
    private String mainTranslation;
    private String secondTranslation;
    private RadioButton rbCostume, rbDic;
    private MaterialButton mButton;

    private InternalViewModel mInternalViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mAdapter = new AddNewLeitnerViewPagerAdapter(getChildFragmentManager());
        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_add_new_leitner, container, false);
        tvTitle = view.findViewById(R.id.tv_title_dialog_add_leitner);
        edtGuide = view.findViewById(R.id.edt_dialog_guide);
        mButton = view.findViewById(R.id.btn_save_item_leitner);
        mPager = view.findViewById(R.id.view_pager_add_new_leitner);
        mTabLayout = view.findViewById(R.id.tab_add_new_leitner);
        rbDic = view.findViewById(R.id.rb_dic_bottom_sheet);
        rbCostume = view.findViewById(R.id.rb_costume_bottom_sheet);
        mPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mPager);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedViewModel.getLeitnerItemData().observe(getViewLifecycleOwner(), strings -> {
            title = strings[0];
            tvTitle.setText(title);

            mainTranslation = strings[1];
            secondTranslation = strings[2];

            mAdapter.addData("Translation", TranslationDialogFragment.newInstance(mainTranslation, KeysValue.FRAGMENT_HEADER_TRANSLATION));
            if (secondTranslation != null) {
                mAdapter.addData("Second Translation", TranslationDialogFragment.newInstance(secondTranslation, KeysValue.FRAGMENT_HEADER_SECOND_TRANSLATION));
            }
        });

        rbCostume.setOnCheckedChangeListener((compoundButton, b) -> mSharedViewModel.setCostumeCheck(b));

        mButton.setOnClickListener(view1 -> {
            mSharedViewModel.setSaveBtnCheck(true);


            Leitner leitner = new Leitner(0, title, mSharedViewModel.getTranslation(), mSharedViewModel.getSecondTranslation(),
                    "bug", edtGuide.getText().toString(), LeitnerStateConstant.BOX_ONE, 0, 0, System.currentTimeMillis());
            mInternalViewModel.insertLeitnerItem(leitner);
            Toast.makeText(view1.getContext(), "Added To leitner!", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

}
