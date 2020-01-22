package com.example.dileit.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentAdvancedSearchResultBinding;
import com.example.dileit.viewmodel.AdvancedDictionaryViewModel;
import com.example.dileit.viewmodel.SharedViewModel;


public class AdvancedSearchResultFragment extends Fragment {

    private SharedViewModel mSharedViewModel;
    private FragmentAdvancedSearchResultBinding mBinding;
    private AdvancedDictionaryViewModel mAdvancedDictionaryViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mAdvancedDictionaryViewModel = ViewModelProviders.of(getActivity()).get(AdvancedDictionaryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_advanced_search_result, container, false);
        return mBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedViewModel.getAdvancedRes().observe(getViewLifecycleOwner() , wordSearches -> {
            mBinding.tvRes.setText(wordSearches.toString());
        });
    }

}
