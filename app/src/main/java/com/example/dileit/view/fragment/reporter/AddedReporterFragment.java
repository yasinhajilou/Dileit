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
import com.example.dileit.databinding.FragmentAddedReporterBinding;
import com.example.dileit.viewmodel.ReporterViewModel;


public class AddedReporterFragment extends Fragment {
    private FragmentAddedReporterBinding mBinding;
    private ReporterViewModel mReporterViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentAddedReporterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReporterViewModel = ViewModelProviders.of(getActivity()).get(ReporterViewModel.class);

        mReporterViewModel.getLiveReportAdded().observe(getViewLifecycleOwner(), leitnerReports -> {

        });

        mReporterViewModel.getTimeFilterFlag().observe(getViewLifecycleOwner(), integer -> {

        });
    }
}