package com.yasinhajilou.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yasinhajilou.dileit.databinding.FragmentRelatedIdiomsBinding;
import com.yasinhajilou.dileit.view.adapter.recycler.IdiomRecyclerAdapter;
import com.yasinhajilou.dileit.viewmodel.SharedViewModel;


public class RelatedIdiomsFragment extends Fragment {

    private SharedViewModel mSharedViewModel;
    private FragmentRelatedIdiomsBinding mBinding;
    private RecyclerView mRecyclerView;
    private IdiomRecyclerAdapter mAdapter;
    private String TAG = RelatedIdiomsFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentRelatedIdiomsBinding.inflate(inflater, container, false);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
