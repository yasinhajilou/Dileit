package com.example.dileit.view.fragment.leitnercardhandler;

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
    private int cardId;
    private Leitner mLeitner;

    public static LeitnerCardModifierBottomSheet onNewInstance(LeitnerModifierConstants constants, int cardId) {
        LeitnerCardModifierBottomSheet bottomSheet = new LeitnerCardModifierBottomSheet();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeysValue.FRAGMENT_LEITNER_CARD_HANDLER, constants);
        bundle.putInt(KeysValue.FRAGMENT_LEITNER_CARD_HANDLER_INT_ID, cardId);
        bottomSheet.setArguments(bundle);
        return bottomSheet;
    }

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
        if (getArguments() != null) {
            mConstants = (LeitnerModifierConstants) getArguments().getSerializable(KeysValue.FRAGMENT_LEITNER_CARD_HANDLER);
            cardId = getArguments().getInt(KeysValue.FRAGMENT_LEITNER_CARD_HANDLER_INT_ID);
        }
        mBinding = BottomSheetAddNewLeitnerBinding.inflate(inflater, container, false);
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
        if (mConstants == LeitnerModifierConstants.ADD) {
            setUpViewForAdding();
        } else {
            //it calls for editing
            if (mConstants == LeitnerModifierConstants.EDIT) {
                setUpViewForEditing();
                mBinding.radioGpDialog.setVisibility(View.INVISIBLE);
            }
        }


        mBinding.btnSaveItemLeitner.setOnClickListener(view1 -> {
            mSharedViewModel.setSaveBtnCheck(true);
            switch (mConstants) {
                case ADD:
                    mLeitner = new Leitner(0, title, mSharedViewModel.getTranslation(), mSharedViewModel.getSecondTranslation(),
                            "bug", mBinding.edtDialogGuide.getText().toString(), LeitnerStateConstant.STARTED, 0, 0, System.currentTimeMillis());
                    mInternalViewModel.insertLeitnerItem(mLeitner);
                    Toast.makeText(view1.getContext(), "Added To leitner!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
                case EDIT:
                    mInternalViewModel.updateLeitnerItem(mLeitner);
                    Toast.makeText(view1.getContext(), "Leitner Card Updated!", Toast.LENGTH_SHORT).show();
                    dismiss();
                    break;
            }

        });

    }

    private void setUpViewForEditing() {
        mInternalViewModel.getLeitnerCardById(cardId).observe(getViewLifecycleOwner(), leitner -> {
            mBinding.tvTitleDialogAddLeitner.setText(leitner.getWord());
            mBinding.edtDialogGuide.setText(leitner.getGuide());

            handleViewPagerItems(leitner.getDef(), leitner.getSecondDef());

            mLeitner = leitner;
        });
    }

    private void setUpViewForAdding() {
        mSharedViewModel.getLeitnerItemData().observe(getViewLifecycleOwner(), strings -> {
            title = strings[0];
            mBinding.tvTitleDialogAddLeitner.setText(title);

            mainTranslation = strings[1];
            secondTranslation = strings[2];

            handleViewPagerItems(mainTranslation, secondTranslation);

        });

        mBinding.rbCostumeBottomSheet.setOnCheckedChangeListener((compoundButton, b) -> mSharedViewModel.setCostumeCheck(b));

    }


    private void handleViewPagerItems(String mainTranslation, String secondTranslation) {
        mAdapter.addData("Translation", TranslationDialogFragment.newInstance(mainTranslation, KeysValue.FRAGMENT_HEADER_TRANSLATION));
        if (secondTranslation != null) {
            mAdapter.addData("Second Translation", TranslationDialogFragment.newInstance(secondTranslation, KeysValue.FRAGMENT_HEADER_SECOND_TRANSLATION));
        }
    }


}
