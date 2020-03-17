package com.example.dileit.view.fragment.wordinfo.leitnersetup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.viewmodel.SharedViewModel;

public class TranslationDialogFragment extends Fragment {
    private SharedViewModel mSharedViewModel;
    private EditText mEditText;

    public TranslationDialogFragment() {
        // Required empty public constructor
    }

    public static TranslationDialogFragment newInstance(String s) {
        TranslationDialogFragment fragment = new TranslationDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KeysValue.KEY_BUNDLE_WORD_TRANSLATION, s);
        fragment.setArguments(bundle);
        return fragment;
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

        if (getArguments() != null)
            translation = getArguments().getString(KeysValue.KEY_BUNDLE_WORD_TRANSLATION);

        if (translation!=null){
            mEditText.setText(translation);
        }


        String firstTranslation = translation;
        mSharedViewModel.getCostumeCheck().observe(getViewLifecycleOwner() , aBoolean -> {
            if (aBoolean)
                mEditText.setEnabled(true);
            else{
                mEditText.setEnabled(false);
                mEditText.setText(firstTranslation.toString());
            }


        });
    }
}
