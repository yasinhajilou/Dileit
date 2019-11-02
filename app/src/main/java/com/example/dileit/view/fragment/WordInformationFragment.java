package com.example.dileit.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentWordInformationBinding;
import com.example.dileit.model.WordDefinition;
import com.example.dileit.viewmodel.SharedViewModel;


public class WordInformationFragment extends Fragment {

    private SharedViewModel mSharedViewModel;
    FragmentWordInformationBinding  mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_word_information , container , false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSharedViewModel.getData().observe(getViewLifecycleOwner(), new Observer<WordDefinition>() {
            @Override
            public void onChanged(WordDefinition wordDefinition) {
                mBinding.tvWordInfo.setText(wordDefinition.getTranslationWords().get(0).getTranslatedWord());
            }
        });
    }
}
