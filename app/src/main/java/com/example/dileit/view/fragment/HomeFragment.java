package com.example.dileit.view.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentHomeBinding;
import com.example.dileit.view.adapter.WordHistoryRecyclerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment {
    private static final int REQ_CODE_SPEECH_TO_TEXT = 12;
    private FragmentHomeBinding mBinding;
    private InternalViewModel mViewModel;
    private WordHistoryRecyclerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
        mAdapter = new WordHistoryRecyclerAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.rvWordHistory.setAdapter(mAdapter);
        mBinding.rvWordHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mViewModel.getLiveData().observe(getViewLifecycleOwner() , wordHistories -> {
            if (wordHistories.size()>0){
                mBinding.tvWordHistoryInfo.setVisibility(View.GONE);
                mBinding.rvWordHistory.setVisibility(View.VISIBLE);
                mAdapter.setData(wordHistories);
            }else {
                mBinding.tvWordHistoryInfo.setVisibility(View.VISIBLE);
                mBinding.rvWordHistory.setVisibility(View.GONE);
            }
        });

        mBinding.linearLayoutVoice.setOnClickListener(view12 -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_TO_TEXT);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.tvHomeWord.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_wordSearchFragment);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH_TO_TEXT) {
            if (data != null) {
                ArrayList res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (res != null) {
                    Toast.makeText(getContext(), res.get(0).toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "is null", Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
