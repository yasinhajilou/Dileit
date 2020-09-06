package com.example.dileit.view.fragment.leitnercardhandler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dileit.R;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.BottomSheetAddCostumeLeitnerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.utils.StringUtils;
import com.example.dileit.viewmodel.InternalViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.Objects;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class BottomSheetAddCostumeLeitner extends BottomSheetDialogFragment {
    private InternalViewModel mViewModel;
    private BottomSheetAddCostumeLeitnerBinding mBinding;
    private Leitner mLeitner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = BottomSheetAddCostumeLeitnerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this::onDestroyView);
            BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.drop_down_card_cat, StringUtils.dropDownItem(getContext()));

        mBinding.filledExposedDropdown.setAdapter(arrayAdapter);

        mBinding.btnSaveCostumeLeitner.setOnClickListener(view1 -> {

            String cardTitle = mBinding.edtInputTitle.getText().toString().toLowerCase();
            String cardDef = mBinding.edtInputDef.getText().toString().trim();
            if (!Objects.equals(cardTitle, "") && !Objects.equals(cardDef, "")) {
                String cat = mBinding.filledExposedDropdown.getText().toString();
                if (!cat.equals("")) {
                    String guide = null;
                    if (mBinding.edtInputGuide.getText() != null)
                        guide = mBinding.edtInputGuide.getText().toString().trim();

                    mLeitner = new Leitner(0, cardTitle, cardDef, null, cat, guide, LeitnerStateConstant.STARTED,
                            0, 0, System.currentTimeMillis());

                    mViewModel.getExistedLeitner(cardTitle);
                } else {
                    Toast.makeText(getContext(), R.string.pl_add_cat, Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getContext(), R.string.pl_fill_fields, Toast.LENGTH_LONG).show();
            }

        });


        mViewModel.getBooleanExistedCard().observe(this, aBoolean -> {
            if (aBoolean)
                Toast.makeText(getContext(), R.string.card_duplicated, Toast.LENGTH_LONG).show();
            else
                mViewModel.insertLeitnerItem(mLeitner);

        });

        mViewModel.getAddedLeitnerItemId().observe(getViewLifecycleOwner(), aLong -> {
            if (aLong > 0) {
                dismiss();
                Toast.makeText(getContext(), R.string.added, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
