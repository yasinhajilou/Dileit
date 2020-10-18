package com.yasinhajilou.dileit.view.fragment.leitnercardhandler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yasinhajilou.dileit.R;
import com.yasinhajilou.dileit.constant.KeysValue;
import com.yasinhajilou.dileit.viewmodel.SharedViewModel;

public class TranslationDialogFragment extends Fragment {
    private SharedViewModel mSharedViewModel;
    private EditText mEditText;
    private byte mHeader;

    public TranslationDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_translation_dialog, container, false);
        mEditText = view.findViewById(R.id.edt_translation_dialog);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String translation = null;

        if (getArguments() != null) {
            translation = getArguments().getString(KeysValue.KEY_BUNDLE_WORD_TRANSLATION);
            mHeader = getArguments().getByte(KeysValue.KEY_BUNDLE_TRANS_HEADER);
        }

        if (translation != null)
            mEditText.setText(translation);


        String firstTranslation = translation;
        mSharedViewModel.getCostumeCheck().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                mEditText.setEnabled(true);
            else {
                mEditText.setEnabled(false);
                mEditText.setText(firstTranslation);
            }
        });

        mSharedViewModel.getSaveButtonCheck().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean){

                //this class serves for both first viewpager fragment and second one we should check to
                // prepare appropriate result

                String trns = mEditText.getText().toString();
                switch (mHeader){
                    case 1:
                        mSharedViewModel.setPerEngTranslation(trns);
                        break;
                    case 2:
                        mSharedViewModel.setSecondTranslation(trns);
                        break;
                }
            }
        });
    }


}
