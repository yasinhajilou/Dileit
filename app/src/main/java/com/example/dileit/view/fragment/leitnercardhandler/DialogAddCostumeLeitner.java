package com.example.dileit.view.fragment.leitnercardhandler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.BottomSheetAddCostumeLeitnerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.viewmodel.InternalViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.Objects;


public class DialogAddCostumeLeitner extends BottomSheetDialogFragment {
    private InternalViewModel mViewModel;
    private BottomSheetAddCostumeLeitnerBinding mBinding;

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

        mBinding.btnSaveCostumeLeitner.setOnClickListener(view1 -> {

            String cardTitle = mBinding.edtInputTitle.getText().toString().toLowerCase();
            String cardDef = mBinding.edtInputDef.getText().toString().trim();
            if (!Objects.equals(cardTitle, "") && !Objects.equals(cardDef, "")) {
                String guide = null;
                if (mBinding.edtInputGuide.getText() != null)
                    guide = mBinding.edtInputGuide.getText().toString().trim();

                Leitner leitner = new Leitner(0, cardTitle, cardDef, null, "bug", guide, LeitnerStateConstant.STARTED,
                        0, 0, System.currentTimeMillis());

                mViewModel.insertLeitnerItem(leitner);
            } else {
                Toast.makeText(getContext(), "please fill fields", Toast.LENGTH_SHORT).show();
            }

        });


        mViewModel.getLeitnerItemId().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if (aLong > 0) {
                    dismiss();
                    Toast.makeText(getContext(), "Added.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "An error occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
