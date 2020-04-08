package com.example.dileit.view.fragment.leitner;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.databinding.FragmentLeitnerItemBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.viewmodel.InternalViewModel;

import java.lang.ref.PhantomReference;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeitnerItemFragment extends Fragment {
    private final String TAG = LeitnerItemFragment.class.getSimpleName();
    private FragmentLeitnerItemBinding mBinding;
    private int listId;
    private InternalViewModel mInternalViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
        if (getArguments() != null)
            listId = getArguments().getInt(KeysValue.KEY_BUNDLE_LEITNER_ITEM_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentLeitnerItemBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInternalViewModel.getAllLeitnerItems().observe(getViewLifecycleOwner(), new Observer<List<Leitner>>() {
            @Override
            public void onChanged(List<Leitner> leitnerList) {
                for (Leitner leitner :
                        leitnerList) {
                    if (leitner.getId() == listId){
                        mBinding.tvWordTitleReviewLeitner.setText(leitner.getWord());
                        break;
                    }

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
