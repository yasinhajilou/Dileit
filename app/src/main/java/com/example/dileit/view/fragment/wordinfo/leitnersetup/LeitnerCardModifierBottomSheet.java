package com.example.dileit.view.fragment.wordinfo.leitnersetup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.dileit.constant.KeysValue;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.BottomSheetAddNewLeitnerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.constant.LeitnerModifierConstants;
import com.example.dileit.view.adapter.viewpager.AddNewLeitnerViewPagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.SharedViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class LeitnerCardModifierBottomSheet extends BottomSheetDialogFragment {
    private final String TAG = LeitnerCardModifierBottomSheet.class.getSimpleName();
    private BottomSheetAddNewLeitnerBinding mBinding;
    private SharedViewModel mSharedViewModel;
    private String title;
    private AddNewLeitnerViewPagerAdapter mAdapter;
    private String mainTranslation;
    private String secondTranslation;
    private InternalViewModel mInternalViewModel;
    private LeitnerModifierConstants mConstants;

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
        if (getArguments()!=null){
            mConstants = (LeitnerModifierConstants) getArguments().getSerializable(KeysValue.FRAGMENT_LEITNER_CARD_HANDLER);
        }
        mBinding = BottomSheetAddNewLeitnerBinding.inflate(inflater , container , false);
        mBinding.viewPagerAddNewLeitner.setAdapter(mAdapter);
        mBinding.tabAddNewLeitner.setupWithViewPager(mBinding.viewPagerAddNewLeitner);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //this class will call in two way:
        //1- when user wants to add a brand new card
        //2- when user wants to edit a card

        //it calls for adding
        if (mConstants == LeitnerModifierConstants.ADD){
            setUpViewForAdding();
        }else {
            //it calls for editing
            if (mConstants == LeitnerModifierConstants.EDIT){
                setUpViewForEditing();
            }
        }

    }

    private void setUpViewForEditing() {

    }

    private void setUpViewForAdding() {
        mSharedViewModel.getLeitnerItemData().observe(getViewLifecycleOwner(), strings -> {
            title = strings[0];
            mBinding.tvTitleDialogAddLeitner.setText(title);

            mainTranslation = strings[1];
            secondTranslation = strings[2];

            mAdapter.addData("Translation", TranslationDialogFragment.newInstance(mainTranslation, KeysValue.FRAGMENT_HEADER_TRANSLATION));
            if (secondTranslation != null) {
                mAdapter.addData("Second Translation", TranslationDialogFragment.newInstance(secondTranslation, KeysValue.FRAGMENT_HEADER_SECOND_TRANSLATION));
            }
        });

        mBinding.rbCostumeBottomSheet.setOnCheckedChangeListener((compoundButton, b) -> mSharedViewModel.setCostumeCheck(b));

        mBinding.btnSaveItemLeitner.setOnClickListener(view1 -> {
            mSharedViewModel.setSaveBtnCheck(true);


            Leitner leitner = new Leitner(0, title, mSharedViewModel.getTranslation(), mSharedViewModel.getSecondTranslation(),
                    "bug", mBinding.edtDialogGuide.getText().toString(), LeitnerStateConstant.STARTED, 0, 0, System.currentTimeMillis());
            mInternalViewModel.insertLeitnerItem(leitner);
            Toast.makeText(view1.getContext(), "Added To leitner!", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }


}
