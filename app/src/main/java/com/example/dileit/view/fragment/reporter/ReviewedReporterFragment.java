package com.example.dileit.view.fragment.reporter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentRelatedIdiomsBinding;
import com.example.dileit.databinding.FragmentReviewedReporterBinding;
import com.example.dileit.viewmodel.ReporterViewModel;


public class ReviewedReporterFragment extends Fragment {

    private FragmentReviewedReporterBinding mBinding;
    private ReporterViewModel mReporterViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentReviewedReporterBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReporterViewModel = ViewModelProviders.of(getActivity()).get(ReporterViewModel.class);


        mReporterViewModel.getLiveReportsReviewed().observe(getViewLifecycleOwner(), wordReviewHistories -> {

        });

        mReporterViewModel.getTimeFilterFlag().observe(getViewLifecycleOwner(), integer -> {

        });
    }
}