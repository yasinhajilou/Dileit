package com.example.dileit.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentWordInformationBinding;
import com.example.dileit.model.TranslationExample;
import com.example.dileit.model.TranslationWord;
import com.example.dileit.view.adapter.WordsInformationViewPagerAdapter;
import com.example.dileit.view.fragment.wordinfo.PersianTranslatedFragment;
import com.example.dileit.viewmodel.SharedViewModel;
import com.google.android.material.chip.Chip;

import java.util.List;
import java.util.Locale;


public class WordInformationFragment extends Fragment {

    private TextToSpeech mTextToSpeechUS;
    private TextToSpeech mTextToSpeechUK;

    private String TAG = WordInformationFragment.class.getSimpleName();
    private SharedViewModel mSharedViewModel;
    private FragmentWordInformationBinding mBinding;
    private Chip chipPersian, chipEnglish, chipIdioms;
    WordsInformationViewPagerAdapter mAdapter;
    int a = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_information, container, false);
        chipPersian = mBinding.chipsTranslatedOnly;
        chipEnglish = mBinding.chipsTranslatedEnglish;
        chipIdioms = mBinding.chipsIdiomsOnly;
        mAdapter = new WordsInformationViewPagerAdapter(getActivity().getSupportFragmentManager());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter.addPage(new PersianTranslatedFragment());
        mBinding.viewPagerWordInfo.setAdapter(mAdapter);
        mBinding.viewPagerWordInfo.setCurrentItem(0);


        mTextToSpeechUK = new TextToSpeech(view.getContext(), i -> {
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

        mTextToSpeechUS = new TextToSpeech(view.getContext(), i -> {
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



        mBinding.imgBritishPronounce.setOnClickListener(view1 -> {
            speakUK(mBinding.tvWordTitle.getText().toString());
        });
        mBinding.tvBritishPronounce.setOnClickListener(view1 -> {
            speakUK(mBinding.tvWordTitle.getText().toString());
        });



        mBinding.imgAmericanPronounce.setOnClickListener(view1 -> {
            speakUS(mBinding.tvWordTitle.getText().toString());
        });
        mBinding.tvAmericanPronounce.setOnClickListener(view1 -> {
            speakUS(mBinding.tvWordTitle.getText().toString());
        });

        mBinding.imgCloseToolBar.setOnClickListener(view12 -> requireActivity().getOnBackPressedDispatcher().onBackPressed());

        mSharedViewModel.getActualWord().observe(getViewLifecycleOwner(), s -> mBinding.tvWordTitle.setText(s));

        mSharedViewModel.getWordInformation().observe(getViewLifecycleOwner(), wordInformation -> {

            mBinding.tvPronounceTitle.setText(wordInformation.getPronunciation());
            StringBuilder stringBuffer = new StringBuilder();
            List<TranslationWord> wordList = wordInformation.getTranslationWords();

            mSharedViewModel.setTranslationWord(wordInformation.getTranslationWords());

            for (int i = 0; i < wordInformation.getTranslationWords().size(); i++) {
                stringBuffer.append(wordList.get(i).getTranslatedWord()).append("\n");
                if (wordList.get(i).getTranslationExamples() != null) {
                    List<TranslationExample> examples = wordList.get(i).getTranslationExamples();
                    for (int j = 0; j < examples.size(); j++) {
                        stringBuffer.append("مثال:").append(examples.get(j).getSentence()).append("\n");
                        stringBuffer.append("معنیش:").append(examples.get(j).getTranslation()).append("\n");
                    }
                }
            }
        });
    }


    private void speakUS(String text) {
        a++;
        if (a % 2 != 0) {
            mTextToSpeechUS.setSpeechRate(0.0f);
        } else {
            mTextToSpeechUS.setSpeechRate(1f);
        }
        mTextToSpeechUS.setPitch(0f);
        mTextToSpeechUS.setLanguage(Locale.US);

        mTextToSpeechUS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speakUK(String text) {
        a++;
        if (a % 2 != 0) {
            mTextToSpeechUK.setSpeechRate(0.0f);
        } else {
            mTextToSpeechUK.setSpeechRate(1f);
        }
        mTextToSpeechUK.setPitch(0f);
        mTextToSpeechUK.setLanguage(Locale.UK);
        mTextToSpeechUK.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
