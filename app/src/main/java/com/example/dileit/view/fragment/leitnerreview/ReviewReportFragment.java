package com.example.dileit.view.fragment.leitnerreview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentReviewReportBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.utils.LeitnerUtils;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.List;

public class ReviewReportFragment extends Fragment {

    private InternalViewModel mInternalViewModel;
    private FragmentReviewReportBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentReviewReportBinding.inflate(inflater , container , false);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
