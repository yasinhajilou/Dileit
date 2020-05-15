package com.example.dileit.view.fragment.leitnermanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentReviewWordsManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

public class ReviewWordsManagerFragment extends Fragment implements LeitnerManagerAdapter.LeitnerManagerInterface {

    private final String TAG = ReviewWordsManagerFragment.class.getSimpleName();
    private FragmentReviewWordsManagerBinding mBinding;
    private InternalViewModel mInternalViewModel;
    private LeitnerManagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentReviewWordsManagerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LeitnerManagerAdapter(this);

        mBinding.rvReviewManager.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvReviewManager.setAdapter(adapter);

        mInternalViewModel.getCardsByRangeState(LeitnerStateConstant.BOX_ONE , LeitnerStateConstant.BOX_FIVE).observe(getViewLifecycleOwner() , leitnerList -> {
            adapter.setData(leitnerList);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onDeleteSelected(Leitner leitner) {
        Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditSelected(Leitner leitner) {
        Toast.makeText(getContext(), "hi", Toast.LENGTH_SHORT).show();
    }
}
