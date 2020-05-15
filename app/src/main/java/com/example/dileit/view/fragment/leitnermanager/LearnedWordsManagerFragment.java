package com.example.dileit.view.fragment.leitnermanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentLearnedWordsManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

public class LearnedWordsManagerFragment extends Fragment implements LeitnerManagerAdapter.LeitnerManagerInterface {

    private InternalViewModel mInternalViewModel;
    private LeitnerManagerAdapter mAdapter;
    private FragmentLearnedWordsManagerBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentLearnedWordsManagerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new LeitnerManagerAdapter(this);

        mBinding.rvLearnedCardManager.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvLearnedCardManager.setAdapter(mAdapter);

        mInternalViewModel.getLearnedCards().observe(getViewLifecycleOwner() , leitnerList -> {
            mAdapter.setData(leitnerList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onDeleteSelected(Leitner leitner) {

    }

    @Override
    public void onEditSelected(Leitner leitner) {

    }
}