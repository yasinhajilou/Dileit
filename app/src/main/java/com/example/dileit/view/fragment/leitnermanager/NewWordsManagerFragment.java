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

import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentNewWordManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerRecyclerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;


public class NewWordsManagerFragment extends Fragment implements LeitnerManagerRecyclerAdapter.LeitnerManagerInterface {

    private static final String TAG = NewWordsManagerFragment.class.getSimpleName();
    private InternalViewModel mInternalViewModel;
    private LeitnerManagerRecyclerAdapter adapter;
    private FragmentNewWordManagerBinding mBinding;
    private boolean notifyRv = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentNewWordManagerBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new LeitnerManagerRecyclerAdapter(this);
        mBinding.rvNewWordManager.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvNewWordManager.setAdapter(adapter);


        mInternalViewModel.getCardsByState(LeitnerStateConstant.STARTED).observe(getViewLifecycleOwner(), leitnerList -> {
            if (leitnerList != null) {
                if (leitnerList.size() > 0) {
                    mBinding.rvNewWordManager.setVisibility(View.VISIBLE);
                    mBinding.tvNoDataNew.setVisibility(View.GONE);
                    adapter.setData(leitnerList, notifyRv);
                    //reset
                    if (!notifyRv)
                        notifyRv = true;
                }
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
        if (adapter.getItemCount() == 0){
            mBinding.rvNewWordManager.setVisibility(View.GONE);
            mBinding.tvNoDataNew.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEditSelected(Leitner leitner) {
        LeitnerManagerHandler.edit(this, leitner);
    }
}
