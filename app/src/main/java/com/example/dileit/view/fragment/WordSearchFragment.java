package com.example.dileit.view.fragment;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.model.Dictionary;
import com.example.dileit.view.adapter.AllWordsRecyclerAdapter;
import com.example.dileit.viewModel.DictionaryViewModel;

import java.util.List;


public class WordSearchFragment extends Fragment {

    private DictionaryViewModel mViewModel;
    private RecyclerView rvWords;
    private AllWordsRecyclerAdapter mAdapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Transition inflater = TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move);
            setSharedElementEnterTransition(inflater);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_search, container, false);
        setUpRecyclerView(view);
        mViewModel = ViewModelProviders.of(this).get(DictionaryViewModel.class);
        mViewModel.setData();
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Dictionary>>() {
            @Override
            public void onChanged(List<Dictionary> dictionaries) {
                mAdapter.setData(dictionaries);
            }
        });
        return view;
    }

    private void setUpRecyclerView(View view) {
        rvWords = view.findViewById(R.id.rv_search_ragment);
        mAdapter = new AllWordsRecyclerAdapter();
        rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWords.setAdapter(mAdapter);
    }


}
