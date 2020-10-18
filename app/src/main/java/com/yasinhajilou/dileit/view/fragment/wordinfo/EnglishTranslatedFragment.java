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

import com.yasinhajilou.dileit.databinding.FragmentEnglishTranslatedBinding;
import com.yasinhajilou.dileit.view.adapter.recycler.EnglishTranslationWordRecyclerAdapter;
import com.yasinhajilou.dileit.viewmodel.SharedViewModel;


public class EnglishTranslatedFragment extends Fragment {

    private FragmentEnglishTranslatedBinding mBinding;
    private SharedViewModel mViewModel;
    private EnglishTranslationWordRecyclerAdapter mAdapter;
    private final String TAG = EnglishTranslatedFragment.class.getSimpleName();

    public static void newInstance(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        mAdapter = new EnglishTranslationWordRecyclerAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentEnglishTranslatedBinding.inflate(inflater,  container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.rvEnglishTranslation.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvEnglishTranslation.setAdapter(mAdapter);

        mViewModel.getEngDefList().observe(getViewLifecycleOwner() , englishDefs -> {
            mAdapter.setData(englishDefs);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
