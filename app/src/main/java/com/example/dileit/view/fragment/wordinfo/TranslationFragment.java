package com.example.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.databinding.FragmentPersianTranslatedBinding;
import com.example.dileit.model.WordInformation;
import com.example.dileit.view.adapter.recycler.TranslationWordRecyclerAdapter;
import com.example.dileit.view.adapter.recycler.WordInfoParentRecyclerAdapter;
import com.example.dileit.viewmodel.SharedViewModel;


public class TranslationFragment extends Fragment {
    private FragmentPersianTranslatedBinding mBinding;
    private SharedViewModel mSharedViewModel;
    private String TAG = TranslationFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentPersianTranslatedBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WordInfoParentRecyclerAdapter wordInfoParentRecyclerAdapter = new WordInfoParentRecyclerAdapter();
        mBinding.rvWordInformation.setAdapter(wordInfoParentRecyclerAdapter);
        mBinding.rvWordInformation.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSharedViewModel.getTranslationWord().observe(getViewLifecycleOwner(), wordInfos -> {
            wordInfoParentRecyclerAdapter.setData(wordInfos);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
