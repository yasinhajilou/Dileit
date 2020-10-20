package com.yasinhajilou.dileit.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import com.yasinhajilou.dileit.constant.KeysValue;
import com.yasinhajilou.dileit.constant.LeitnerStateConstant;
import com.yasinhajilou.dileit.databinding.ActivityReviewLeitnerBinding;
import com.yasinhajilou.dileit.model.entity.Leitner;
import com.yasinhajilou.dileit.view.fragment.leitnerreview.ReviewReportFragment;
import com.yasinhajilou.dileit.view.adapter.viewpager.LeitnerReviewViewPagerAdapter;
import com.yasinhajilou.dileit.view.fragment.leitnerreview.InterfaceReviewButtonClickListener;
import com.yasinhajilou.dileit.view.fragment.leitnerreview.LeitnerItemFragment;
import com.yasinhajilou.dileit.viewmodel.InternalViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class ReviewLeitnerActivity extends AppCompatActivity implements InterfaceReviewButtonClickListener {

    private static final String TAG = ReviewLeitnerActivity.class.getSimpleName();
    private TextToSpeech mTextToSpeechUS;
    private TextToSpeech mTextToSpeechUK;

    private LeitnerReviewViewPagerAdapter mAdapter;
    private ActivityReviewLeitnerBinding mBinding;
    private List<Fragment> fragments;


    private boolean isStartUp = true;
    private int newWords = 0;
    private int currentPosIndex = 0;
    private List<Leitner> filteredList;
    private int cardsSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReviewLeitnerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        InternalViewModel viewModel = new ViewModelProvider(this).get(InternalViewModel.class);

        mAdapter = new LeitnerReviewViewPagerAdapter(getSupportFragmentManager());
        mBinding.viewPagerReviewLeitner.setAdapter(mAdapter);


        //setting up progress bar for showing percent with number
        CircularProgressIndicator.ProgressTextAdapter progressTextAdapter = currentProgress -> {
            int a = (int) currentProgress;
            return a + "%";
        };


        mBinding.progressCircularReviewBar.setProgressTextAdapter(progressTextAdapter);
        mBinding.progressCircularReviewBar.setMaxProgress(100);
        mBinding.progressCircularReviewBar.setCurrentProgress(0);

        viewModel.getTodayList().observe(this, leitners -> {

            if (isStartUp) {
                filteredList = leitners;
                fragments = new ArrayList<>();

                for (int i = 0; i < filteredList.size(); i++) {
                    LeitnerItemFragment itemFragment = new LeitnerItemFragment();
                    Bundle bundle = new Bundle();
                    //pass item id to fragment
                    bundle.putInt(KeysValue.KEY_BUNDLE_LEITNER_ITEM_ID, filteredList.get(i).getId());
                    itemFragment.setArguments(bundle);
                    fragments.add(itemFragment);

                    if (filteredList.get(i).getState() == LeitnerStateConstant.STARTED)
                        newWords++;

                }
                cardsSize = fragments.size();
                mBinding.tvReviewCountNew.setText(String.valueOf(newWords));
                mBinding.tvReviewCountToday.setText(String.valueOf(cardsSize));
                mAdapter.addData(fragments);
                isStartUp = false;
            }
        });

        viewModel.getDeletedLeitnerItemStatus().observe(this, integer -> {
            if (integer > 0) {
                int fragmentSizeIndex = fragments.size() - 1;
                if (++currentPosIndex <= fragmentSizeIndex) {
                    mBinding.viewPagerReviewLeitner.setCurrentItem(currentPosIndex);
                    headerCounterHandler(fragments.size(), currentPosIndex);
                } else {
                    goToReviewedCardReporter();
                }

            }
        });
        mTextToSpeechUK = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                int res = mTextToSpeechUS.setLanguage(Locale.UK);
                if (res == TextToSpeech.LANG_MISSING_DATA
                        || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                } else {
                    // prepare ui
                }
            }
        });

        mTextToSpeechUS = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                int res = mTextToSpeechUS.setLanguage(Locale.ENGLISH);
            }
        });


        mBinding.tvBritishPronounceLeitnerRev.setOnClickListener(view -> speakUK(filteredList.get(currentPosIndex).getWord()));

        mBinding.tvAmericanPronounceLeitnerRev.setOnClickListener(view -> speakUS(filteredList.get(currentPosIndex).getWord()));

        mBinding.imgAmericanPronounceLeitnerRev.setOnClickListener(view -> speakUS(filteredList.get(currentPosIndex).getWord()));

        mBinding.imgBritishPronounceLeitnerRev.setOnClickListener(view -> speakUK(filteredList.get(currentPosIndex).getWord()));
    }

    @Override
    public void onYesClicked() {
        handleNextItem();
    }

    @Override
    public void onNoClicked() {
        handleNextItem();
    }


    private void handleNextItem() {
        currentPosIndex++;
        int nextItem = mBinding.viewPagerReviewLeitner.getCurrentItem() + 1;

        //list size for indexing (-1)
        int listSize = fragments.size() - 1;

        //calculate percent amount of reviewed cards
        float a = mBinding.viewPagerReviewLeitner.getCurrentItem() + 1;
        float num = a / fragments.size();
        float percentageProgress = num * 100;
        mBinding.progressCircularReviewBar.setCurrentProgress(percentageProgress);


        if (nextItem <= listSize) {
            mBinding.viewPagerReviewLeitner.setCurrentItem(nextItem);
        } else {
            goToReviewedCardReporter();
        }

        headerCounterHandler(fragments.size(), nextItem);

    }

    private void speakUS(String text) {
        mTextToSpeechUS.setSpeechRate(0.5f);

        mTextToSpeechUS.setPitch(0f);
        mTextToSpeechUS.setLanguage(Locale.US);

        mTextToSpeechUS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speakUK(String text) {
        mTextToSpeechUK.setSpeechRate(0.5f);

        mTextToSpeechUK.setPitch(0f);
        mTextToSpeechUK.setLanguage(Locale.UK);
        mTextToSpeechUK.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void headerCounterHandler(int totalSize, int nextItemIndex) {
        mBinding.tvReviewCountToday.setText(String.valueOf(totalSize - nextItemIndex));
        if (--newWords >= 0)
            mBinding.tvReviewCountNew.setText(String.valueOf(newWords));
    }

    private void goToReviewedCardReporter() {
        mAdapter.addReportView(new ReviewReportFragment());
        //go to last pager which created by above code
        mBinding.viewPagerReviewLeitner.setCurrentItem(mBinding.viewPagerReviewLeitner.getCurrentItem() + 1);
        mBinding.linearLayoutPronounceBar.setVisibility(View.INVISIBLE);
        mBinding.cardHeaderCounter.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        if (mTextToSpeechUK != null) {

            mTextToSpeechUK.stop();
            mTextToSpeechUK.shutdown();
        }
        if (mTextToSpeechUS != null) {

            mTextToSpeechUS.stop();
            mTextToSpeechUS.shutdown();
        }
        super.onDestroy();
    }


}
