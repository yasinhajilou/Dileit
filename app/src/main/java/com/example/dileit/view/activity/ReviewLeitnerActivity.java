package com.example.dileit.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.example.dileit.constant.KeysValue;
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
        mBinding.progressCircularReviewBar.setCurrentProgress(47);

        mViewModel.getAllLeitnerItems().observe(this, leitnerList -> {
            List<Leitner> filteredList = LeitnerUtils.getPreparedLeitnerItems(leitnerList);
            fragments = new ArrayList<>();

            for (int i = 0; i < filteredList.size(); i++) {
                LeitnerItemFragment itemFragment = new LeitnerItemFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(KeysValue.KEY_BUNDLE_LEITNER_ITEM_ID, filteredList.get(i).getId());
                itemFragment.setArguments(bundle);
                fragments.add(itemFragment);
            }
            mAdapter.addData(fragments);
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
    }

    @Override
    public void onYesClicked() {
        handleNextItem();
    }

    @Override
    public void onNoClicked() {
        handleNextItem();
    }

    @Override
    public void onBritishPronounce(String word) {
        speakUK(word);
    }

    @Override
    public void onAmericanPronounce(String word) {
        speakUS(word);
    }

    private void handleNextItem() {
        int nextItem = mBinding.viewPagerReviewLeitner.getCurrentItem() + 1;
        int listSize = fragments.size() - 1;
        if (nextItem <= listSize) {
            mBinding.viewPagerReviewLeitner.setCurrentItem(nextItem);
        } else {
            Toast.makeText(this, "finished", Toast.LENGTH_SHORT).show();
        }
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
}
