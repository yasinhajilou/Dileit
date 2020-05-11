package com.example.dileit.view.fragment.leitnermanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentNewWordManagerBinding;
import com.example.dileit.view.adapter.recycler.LeitnerManagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;


public class NewWordsManagerFragment extends Fragment {

    private InternalViewModel mInternalViewModel;
    private LeitnerManagerAdapter adapter;
    private FragmentNewWordManagerBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentNewWordManagerBinding.inflate(inflater , container , false);
        return mBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
