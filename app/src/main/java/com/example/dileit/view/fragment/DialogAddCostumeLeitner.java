package com.example.dileit.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.BottomSheetAddCostumeLeitnerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.model.repository.InternalRepository;
import com.example.dileit.viewmodel.InternalViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_add_costume_leitner, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.btnSaveCostumeLeitner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.edtInputTitle.getText() != null || mBinding.edtInputDef.getText() != null) {
                    String title = mBinding.edtInputTitle.getText().toString().trim();
                    String def = mBinding.edtInputDef.getText().toString().trim();
                    String guide = null;
                    if (mBinding.edtInputGuide.getText() != null)
                        guide = mBinding.edtInputGuide.getText().toString().trim();

                    Leitner leitner = new Leitner(0,title,def,null,"bug" , guide , LeitnerStateConstant.BOX_ONE,
                            0 , 0 , System.currentTimeMillis());

                    mViewModel.insertLeitnerItem(leitner);
                    dismiss();
                    Toast.makeText(view.getContext(), "Added.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "please fill fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
