package com.example.dileit.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dileit.R;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.ActivityLeitnerManagerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.view.adapter.viewpager.LeitnerManagerViewPagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.List;

public class LeitnerManagerActivity extends AppCompatActivity {

    private InternalViewModel mInternalViewModel;
    ActivityLeitnerManagerBinding mBinding;
    LeitnerManagerViewPagerAdapter mAdapter;
    private int[] tabIcons = {R.drawable.ic_new_24dp
            , R.drawable.ic_searched_24dp, R.drawable.ic_done_all_24dp};
    private int newCardsCounter, learnedCardsCounter, reviewedCardsCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLeitnerManagerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setSupportActionBar(mBinding.toolbarLeitnerManager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
        mAdapter = new LeitnerManagerViewPagerAdapter(getSupportFragmentManager());

        mBinding.viewPagerLeitnerManager.setAdapter(mAdapter);
        mBinding.tabLeitnerManager.setupWithViewPager(mBinding.viewPagerLeitnerManager);
        setTabIcons(tabIcons);


        mInternalViewModel.getAllLeitnerItems().observe(this, new Observer<List<Leitner>>() {
            @Override
            public void onChanged(List<Leitner> leitnerList) {
                for (Leitner leitner :
                        leitnerList) {
                    switch (leitner.getState()) {
                        case LeitnerStateConstant.STARTED:
                            newCardsCounter++;
                            break;
                        case LeitnerStateConstant.LEARNED:
                            learnedCardsCounter++;
                            break;
                        default:
                            reviewedCardsCounter++;
                    }
                }

                showHeaderCountersWithAnimation(newCardsCounter , reviewedCardsCounter , learnedCardsCounter);
            }
        });
    }

    private void setTabIcons(int[] icons) {
        mBinding.tabLeitnerManager.getTabAt(0).setIcon(icons[0]);
        mBinding.tabLeitnerManager.getTabAt(1).setIcon(icons[1]);
        mBinding.tabLeitnerManager.getTabAt(2).setIcon(icons[2]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_leitner_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;
    }

    private void showHeaderCountersWithAnimation(int newCounts , int reviewedCounts , int learnedCounts){
        long duration = 2000;

        //new cards
        ValueAnimator newCardsAnim = ValueAnimator.ofInt(0,newCounts);
        newCardsAnim.setDuration(duration);
        newCardsAnim.addUpdateListener(valueAnimator -> mBinding.tvManagerNew.setText(valueAnimator.getAnimatedValue().toString()));

        //reviewed cards
        ValueAnimator reviewCardsAnim = ValueAnimator.ofInt(0 , reviewedCounts);
        reviewCardsAnim.setDuration(duration);
        reviewCardsAnim.addUpdateListener(valueAnimator -> mBinding.tvManagerReview.setText(valueAnimator.getAnimatedValue().toString()));

        //learned cards
        ValueAnimator learnedCardsAnim = ValueAnimator.ofInt(0 , learnedCounts);
        learnedCardsAnim.setDuration(duration);
        learnedCardsAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBinding.tvManagerLearned.setText(valueAnimator.getAnimatedValue().toString());
            }
        });

        newCardsAnim.start();
        learnedCardsAnim.start();
        reviewCardsAnim.start();
    }
}
