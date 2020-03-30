package com.example.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.databinding.ActivityReviewLeitnerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.utils.LeitnerFilterUtils;
import com.example.dileit.view.adapter.viewpager.LeitnerReviewViewPagerAdapter;
import com.example.dileit.view.fragment.leitner.LeitnerItemFragment;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReviewLeitnerActivity extends AppCompatActivity {

    InternalViewModel mViewModel;
    LeitnerReviewViewPagerAdapter mAdapter;
    ActivityReviewLeitnerBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReviewLeitnerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);

        mAdapter = new LeitnerReviewViewPagerAdapter(getSupportFragmentManager());
        mBinding.viewPagerReviewLeitner.setAdapter(mAdapter);

        mViewModel.getAllLeitnerItems().observe(this, leitnerList -> {
            List<Leitner> filteredList = LeitnerFilterUtils.getPreparedLeitnerItems(leitnerList);
            List<Fragment> fragments = new ArrayList<>();

            for (int i = 0; i < filteredList.size(); i++) {
                LeitnerItemFragment itemFragment = new LeitnerItemFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(KeysValue.KEY_BUNDLE_LEITNER_ITEM_ID, filteredList.get(i).getId());
                itemFragment.setArguments(bundle);
                fragments.add(itemFragment);
            }
            mAdapter.addData(fragments);
        });
    }
}
