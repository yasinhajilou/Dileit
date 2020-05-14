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

import com.example.dileit.R;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentNewWordManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.recycler.LeitnerManagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;


public class NewWordsManagerFragment extends Fragment implements LeitnerManagerAdapter.LeitnerManagerInterface {

    private static final String TAG = NewWordsManagerFragment.class.getSimpleName();
    private InternalViewModel mInternalViewModel;
    private LeitnerManagerAdapter adapter;
    private FragmentNewWordManagerBinding mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
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
        adapter = new LeitnerManagerAdapter(this);
        mBinding.rvNewWordManager.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mBinding.rvNewWordManager.setAdapter(adapter);


        mInternalViewModel.getAllLeitnerItems().observe(getViewLifecycleOwner(), leitnerList -> {

            for (int i = 0; i < leitnerList.size(); i++) {
                if (leitnerList.get(i).getState() != LeitnerStateConstant.STARTED) {
                    leitnerList.remove(i);
                }
            }

            Log.d(TAG, "new :" + leitnerList.size() );

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

    }

    @Override
    public void onEditSelected(Leitner leitner) {

    }
}
