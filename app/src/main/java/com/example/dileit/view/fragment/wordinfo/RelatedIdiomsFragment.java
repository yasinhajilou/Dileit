package com.example.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentRelatedIdiomsBinding;
import com.example.dileit.model.Idiom;
import com.example.dileit.view.adapter.IdiomRecyclerAdapter;
import com.example.dileit.view.adapter.TranslationWordRecyclerAdapter;
import com.example.dileit.viewmodel.SharedViewModel;


public class RelatedIdiomsFragment extends Fragment {

    SharedViewModel mSharedViewModel;
    FragmentRelatedIdiomsBinding mBinding;
    RecyclerView mRecyclerView;
    IdiomRecyclerAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_related_idioms , container, false);
        mRecyclerView = mBinding.rvIdioms;
        mAdapter = new IdiomRecyclerAdapter();
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mSharedViewModel.getIdiom().observe(getViewLifecycleOwner(), idioms -> {
            mAdapter.setData(idioms);
        });
    }
}
