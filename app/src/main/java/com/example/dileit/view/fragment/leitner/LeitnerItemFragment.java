package com.example.dileit.view.fragment.leitner;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentLeitnerItemBinding;

import java.lang.ref.PhantomReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeitnerItemFragment extends Fragment {

    private FragmentLeitnerItemBinding mBinding;
    public LeitnerItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentLeitnerItemBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
