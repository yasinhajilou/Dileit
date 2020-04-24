package com.example.dileit.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.animation.Animator;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

import com.example.dileit.constant.KeysValue;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.ActivityReviewLeitnerBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.utils.LeitnerUtils;
import com.example.dileit.view.adapter.viewpager.LeitnerReviewViewPagerAdapter;
import com.example.dileit.view.fragment.leitner.InterfaceReviewButtonClickListener;
import com.example.dileit.view.fragment.leitner.LeitnerItemFragment;
import com.example.dileit.viewmodel.InternalViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator;

public class ReviewLeitnerActivity extends AppCompatActivity implements InterfaceReviewButtonClickListener {

    private static final String TAG = ReviewLeitnerActivity.class.getSimpleName();
    private TextToSpeech mTextToSpeechUS;
    private TextToSpeech mTextToSpeechUK;

    private InternalViewModel mViewModel;
    private LeitnerReviewViewPagerAdapter mAdapter;
    private ActivityReviewLeitnerBinding mBinding;
    private List<Fragment> fragments;

    private int a, b = 0;

    private float percentageProgress;

    private boolean isStartUp = true;
    private int newWords = 0;

    private int currentPos;
    private List<Leitner> filteredList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReviewLeitnerBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);

        mAdapter = new LeitnerReviewViewPagerAdapter(getSupportFragmentManager());
        mBinding.viewPagerReviewLeitner.setAdapter(mAdapter);


        CircularProgressIndicator.ProgressTextAdapter progressTextAdapter = currentProgress -> {
            int a = (int) currentProgress;
            return a + "%";
        };


        mBinding.progressCircularReviewBar.setProgressTextAdapter(progressTextAdapter);
        mBinding.progressCircularReviewBar.setMaxProgress(100);
        mBinding.progressCircularReviewBar.setCurrentProgress(0);

        mViewModel.getAllLeitnerItems().observe(this, leitnerList -> {
            if (isStartUp){
                filteredList = LeitnerUtils.getPreparedLeitnerItems(leitnerList);
                fragments = new ArrayList<>();

                for (int i = 0; i < filteredList.size(); i++) {
                    LeitnerItemFragment itemFragment = new LeitnerItemFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(KeysValue.KEY_BUNDLE_LEITNER_ITEM_ID, filteredList.get(i).getId());
                    itemFragment.setArguments(bundle);
                    fragments.add(itemFragment);
                    if (filteredList.get(i).getState() == LeitnerStateConstant.STARTED)
                        newWords++;

                }
                mBinding.tvReviewCountNew.setText(String.valueOf(newWords));
                mBinding.tvReviewCountToday.setText(String.valueOf(fragments.size()));
                mAdapter.addData(fragments);
                isStartUp= false;
            }

        });

        mTextToSpeechUK = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                int res = mTextToSpeechUS.setLanguage(Locale.UK);
                if (res == TextToSpeech.LANG_MISSING_DATA
                        || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                } else {
                    // prepare ui
                }
            } else {
                Log.d(TAG, "onViewCreated: " + "TTS init failed...");
            }
        });

        mTextToSpeechUS = new TextToSpeech(this, i -> {
            if (i == TextToSpeech.SUCCESS) {
                int res = mTextToSpeechUS.setLanguage(Locale.ENGLISH);
                if (res == TextToSpeech.LANG_MISSING_DATA
                        || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                } else {

                }
            } else {
                Log.d(TAG, "onViewCreated: " + "TTS init failed...");
            }
        });


        mBinding.tvBritishPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakUK(filteredList.get(currentPos).getWord());
            }
        });

        mBinding.tvAmericanPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakUS(filteredList.get(currentPos).getWord());

            }
        });

        mBinding.imgAmericanPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakUS(filteredList.get(currentPos).getWord());

            }
        });

        mBinding.imgBritishPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakUK(filteredList.get(currentPos).getWord());

            }
        });
    }

    @Override
    public void onYesClicked() {
        handleNextItem();
    }

    @Override
    public void onNoClicked() {
        handleNextItem();
    }

//    @Override
//    public void onBritishPronounce(String word) {
//
//    }
//
//    @Override
//    public void onAmericanPronounce(String word) {
//
//    }

    private void handleNextItem() {
        currentPos++;
        int nextItem = mBinding.viewPagerReviewLeitner.getCurrentItem() + 1;
        int listSize = fragments.size() - 1;
        float a = mBinding.viewPagerReviewLeitner.getCurrentItem() + 1;
        float num = a / fragments.size();
        percentageProgress = num * 100;
        mBinding.progressCircularReviewBar.setCurrentProgress(percentageProgress);
        if (nextItem <= listSize) {
            mBinding.viewPagerReviewLeitner.setCurrentItem(nextItem);
        } else {
            Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show();
        }
        mBinding.tvReviewCountToday.setText(String.valueOf(fragments.size()- nextItem));
        if (--newWords >= 0)
            mBinding.tvReviewCountNew.setText(String.valueOf(newWords));
    }

    private void speakUS(String text) {
        a++;
        if (a % 2 != 0) {
            mTextToSpeechUS.setSpeechRate(0.0f);
        } else {
            mTextToSpeechUS.setSpeechRate(0.6f);
        }
        mTextToSpeechUS.setPitch(0f);
        mTextToSpeechUS.setLanguage(Locale.US);

        mTextToSpeechUS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speakUK(String text) {
        b++;
        if (b % 2 != 0) {
            mTextToSpeechUK.setSpeechRate(0.0f);
        } else {
            mTextToSpeechUK.setSpeechRate(0.6f);
        }
        mTextToSpeechUK.setPitch(0f);
        mTextToSpeechUK.setLanguage(Locale.UK);
        mTextToSpeechUK.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onDestroy() {
        if(mTextToSpeechUK != null){

            mTextToSpeechUK.stop();
            mTextToSpeechUK.shutdown();
        }
        if(mTextToSpeechUS != null){

            mTextToSpeechUS.stop();
            mTextToSpeechUS.shutdown();
        }
        super.onDestroy();
    }


}
