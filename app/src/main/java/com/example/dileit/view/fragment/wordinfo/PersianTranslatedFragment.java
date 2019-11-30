package com.example.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentPersianTranslatedBinding;
import com.example.dileit.view.adapter.TranslationWordRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersianTranslatedFragment extends Fragment {

    FragmentPersianTranslatedBinding mBinding;
    public PersianTranslatedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_persian_translated , container , false );
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TranslationWordRecyclerAdapter adapter = new TranslationWordRecyclerAdapter();
        mBinding.rvPersianTranslationWord.setAdapter(adapter);
        mBinding.rvPersianTranslationWord.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}
