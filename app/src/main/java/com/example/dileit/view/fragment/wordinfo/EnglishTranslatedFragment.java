package com.example.dileit.view.fragment.wordinfo;


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
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentEnglishTranslatedBinding;
import com.example.dileit.model.WordEnglishDic;
import com.example.dileit.viewmodel.EnglishDictionaryViewModel;
import com.example.dileit.viewmodel.SharedViewModel;

import java.util.List;


public class EnglishTranslatedFragment extends Fragment {
    private FragmentEnglishTranslatedBinding mBinding;
    private EnglishDictionaryViewModel mViewModel;
    private SharedViewModel mSharedViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EnglishDictionaryViewModel.class);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_english_translated, container , false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedViewModel.getActualWord().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mViewModel.doSearch(s.trim());
                Toast.makeText(getContext(), "doing search", Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getLiveList().observe(getViewLifecycleOwner(), new Observer<List<WordEnglishDic>>() {
            @Override
            public void onChanged(List<WordEnglishDic> wordEnglishDics) {
                Toast.makeText(getContext(), ""+ wordEnglishDics.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
