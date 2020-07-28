package com.example.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.databinding.FragmentPersianTranslatedBinding;
import com.example.dileit.view.adapter.recycler.TranslationWordRecyclerAdapter;
import com.example.dileit.viewmodel.SharedViewModel;


public class TranslationFragment extends Fragment {
    private FragmentPersianTranslatedBinding mBinding;
    private SharedViewModel mSharedViewModel;
    private String TAG = TranslationFragment.class.getSimpleName();
    private TranslationWordRecyclerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPersianTranslatedBinding.inflate(inflater , container , false );
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TranslationWordRecyclerAdapter();
        mBinding.rvPersianTranslationWord.setAdapter(adapter);
        mBinding.rvPersianTranslationWord.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSharedViewModel.getTranslationWord().observe(getViewLifecycleOwner(), translationWords -> {
            adapter.setData(translationWords);
        });
        mSharedViewModel.getWordCatInTranslation().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.tvWordCatTranslation.setText(s);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
