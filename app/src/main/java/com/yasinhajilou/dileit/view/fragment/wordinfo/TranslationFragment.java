package com.yasinhajilou.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasinhajilou.dileit.databinding.FragmentPersianTranslatedBinding;
import com.yasinhajilou.dileit.view.adapter.recycler.WordInfoParentRecyclerAdapter;
import com.yasinhajilou.dileit.viewmodel.SharedViewModel;


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
