package com.example.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentEnglishTranslatedBinding;
import com.example.dileit.view.adapter.recycler.EnglishTranslationWordRecyclerAdapter;
import com.example.dileit.viewmodel.EnglishDictionaryViewModel;
import com.example.dileit.viewmodel.SharedViewModel;


public class EnglishTranslatedFragment extends Fragment {

    private FragmentEnglishTranslatedBinding mBinding;
    private EnglishDictionaryViewModel mViewModel;
    private SharedViewModel mSharedViewModel;
    private EnglishTranslationWordRecyclerAdapter mAdapter;
    private final String TAG = EnglishTranslatedFragment.class.getSimpleName();

    public static void newInstance(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(EnglishDictionaryViewModel.class);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mAdapter = new EnglishTranslationWordRecyclerAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_english_translated, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, "onViewCreated: ");

        mBinding.rvEnglishTranslation.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvEnglishTranslation.setAdapter(mAdapter);

        mViewModel.getLiveList().observe(getViewLifecycleOwner() , wordEnglishDics -> {
            mAdapter.setData(wordEnglishDics);
        });

    }
}
