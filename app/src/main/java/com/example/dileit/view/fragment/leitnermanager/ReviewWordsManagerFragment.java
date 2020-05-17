package com.example.dileit.view.fragment.leitnermanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentReviewWordsManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerRecyclerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

public class ReviewWordsManagerFragment extends Fragment implements LeitnerManagerRecyclerAdapter.LeitnerManagerInterface {

    private final String TAG = ReviewWordsManagerFragment.class.getSimpleName();
    private FragmentReviewWordsManagerBinding mBinding;
    private InternalViewModel mInternalViewModel;
    private LeitnerManagerRecyclerAdapter adapter;
    private boolean notifyRv = true;

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
        adapter = new LeitnerManagerRecyclerAdapter(this);

        mBinding.rvReviewManager.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvReviewManager.setAdapter(adapter);

        mBinding.chipFilteredBoxOne.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mInternalViewModel.getCardsByState(LeitnerStateConstant.BOX_ONE).observe(getViewLifecycleOwner(), leitnerList -> {
                    adapter.setData(leitnerList, notifyRv);
                    //reset
                    if (!notifyRv)
                        notifyRv = true;
                });
            }
        });

        mBinding.chipFilteredBoxTwo.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mInternalViewModel.getCardsByState(LeitnerStateConstant.BOX_TWO).observe(getViewLifecycleOwner(), leitnerList -> {
                    adapter.setData(leitnerList, notifyRv);
                    //reset
                    if (!notifyRv)
                        notifyRv = true;
                });
            }
        });


        mBinding.chipFilteredBoxThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    mInternalViewModel.getCardsByState(LeitnerStateConstant.BOX_THREE).observe(getViewLifecycleOwner(), leitnerList -> {
                        adapter.setData(leitnerList, notifyRv);
                        //reset
                        if (!notifyRv)
                            notifyRv = true;
                    });
                }
            }
        });


        mBinding.chipFilteredBoxFour.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mInternalViewModel.getCardsByState(LeitnerStateConstant.BOX_FOUR).observe(getViewLifecycleOwner(), leitnerList -> {
                    adapter.setData(leitnerList, notifyRv);
                    //reset
                    if (!notifyRv)
                        notifyRv = true;
                });
            }
        });

        mBinding.chipFilteredBoxFive.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                mInternalViewModel.getCardsByState(LeitnerStateConstant.BOX_FIVE).observe(getViewLifecycleOwner(), leitnerList -> {
                    adapter.setData(leitnerList, notifyRv);
                    //reset
                    if (!notifyRv)
                        notifyRv = true;
                });
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
        notifyRv = false;
        mInternalViewModel.deleteLeitnerItem(leitner);
    }

    @Override
    public void onEditSelected(Leitner leitner) {
        LeitnerManagerHandler.edit(this, leitner);
    }
}
