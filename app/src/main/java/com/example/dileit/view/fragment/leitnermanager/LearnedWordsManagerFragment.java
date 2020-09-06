package com.example.dileit.view.fragment.leitnermanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentLearnedWordsManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerRecyclerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

public class LearnedWordsManagerFragment extends Fragment implements LeitnerManagerRecyclerAdapter.LeitnerManagerInterface {

    private InternalViewModel mInternalViewModel;
    private LeitnerManagerRecyclerAdapter mAdapter;
    private FragmentLearnedWordsManagerBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
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

        mAdapter = new LeitnerManagerRecyclerAdapter(this);

        mBinding.rvLearnedCardManager.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvLearnedCardManager.setAdapter(mAdapter);

        mInternalViewModel.getCardsByState(LeitnerStateConstant.LEARNED).observe(getViewLifecycleOwner(), leitnerList -> {
            if (leitnerList.size() > 0) {
                mBinding.rvLearnedCardManager.setVisibility(View.VISIBLE);
                mBinding.tvNoDataLearned.setVisibility(View.GONE);
                mAdapter.setData(leitnerList);
            }

        });


        mInternalViewModel.getDeletedLeitnerItemStatus().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0) {
                    Toast.makeText(getContext(), R.string.item_deleted, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onDeleteSelected(Leitner leitner) {
        mInternalViewModel.deleteLeitnerItem(leitner);
        if (mAdapter.getItemCount() == 0) {
            mBinding.rvLearnedCardManager.setVisibility(View.GONE);
            mBinding.tvNoDataLearned.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEditSelected(Leitner leitner) {
        LeitnerManagerHandler.edit(this, leitner);
    }
}
