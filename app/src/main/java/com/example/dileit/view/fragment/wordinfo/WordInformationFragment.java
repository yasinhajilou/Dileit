package com.example.dileit.view.fragment.wordinfo;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.constant.LeitnerModifierConstants;
import com.example.dileit.databinding.FragmentWordInformationBinding;
import com.example.dileit.model.Idiom;
import com.example.dileit.model.TranslationWord;
import com.example.dileit.model.EnglishDef;
import com.example.dileit.model.WordInformation;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.utils.JsonUtils;
import com.example.dileit.view.adapter.viewpager.WordsInformationViewPagerAdapter;
import com.example.dileit.view.fragment.leitnercardhandler.LeitnerCardModifierBottomSheet;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.SharedViewModel;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class WordInformationFragment extends Fragment {

    private TextToSpeech mTextToSpeechUS;
    private TextToSpeech mTextToSpeechUK;
    private int a, b = 0;

    private String TAG = WordInformationFragment.class.getSimpleName();
    private SharedViewModel mSharedViewModel;
    private FragmentWordInformationBinding mBinding;
    private Chip chipPersian, chipEnglish, chipIdioms;
    private WordsInformationViewPagerAdapter mAdapter;
    private List<TranslationWord> wordList;
    private boolean isIdiomAvailable = false;
    private EnglishDictionaryViewModel mEnglishDictionaryViewModel;
    private InternalViewModel mInternalViewModel;
    private PersianDictionaryViewModel mPersionDictionaryViewModel;

    private StringBuilder builderEnglish;
    private StringBuilder builderTranslation;

    private String actualWord;
    private int engId;

    private Leitner mLeitner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mEnglishDictionaryViewModel = ViewModelProviders.of(this).get(EnglishDictionaryViewModel.class);
        mInternalViewModel = ViewModelProviders.of(this).get(InternalViewModel.class);
        mPersionDictionaryViewModel = ViewModelProviders.of(this).get(PersianDictionaryViewModel.class);
        if (getArguments() != null) {
            actualWord = getArguments().getString(KeysValue.KEY_BUNDLE_ACTUAL_WORD);
            engId = getArguments().getInt(KeysValue.KEY_BUNDLE_WORD_REF_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentWordInformationBinding.inflate(inflater, container, false);
        mAdapter = new WordsInformationViewPagerAdapter(getChildFragmentManager());
        chipPersian = mBinding.chipsTranslatedOnly;
        chipEnglish = mBinding.chipsTranslatedEnglish;
        chipIdioms = mBinding.chipsIdiomsOnly;
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.viewPagerWordInfo.setAdapter(mAdapter);

        mBinding.tvWordTitle.setText(actualWord);

        mPersionDictionaryViewModel.searchForExactWord(actualWord).observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                mAdapter.addPage(new TranslationFragment());

                chipPersian.setVisibility(View.VISIBLE);
                JsonUtils jsonUtils = new JsonUtils();
                WordInformation[] wordInformations = jsonUtils.getWordDefinition(s);
                List<Idiom> idioms = new ArrayList<>();
                wordList = new ArrayList<>();
                for (WordInformation information : wordInformations) {
                    wordList.addAll(information.getTranslationWords());
                    if (information.getIdioms() != null)
                        idioms.addAll(information.getIdioms());
                }
                mSharedViewModel.setTranslationWord(wordList);

                if (idioms.size() > 0) {
                    chipIdioms.setVisibility(View.VISIBLE);
                    mAdapter.addPage(new RelatedIdiomsFragment());
                    isIdiomAvailable = true;
                    mSharedViewModel.setIdiom(idioms);
                }
            }
        });

        mEnglishDictionaryViewModel.searchEngWordById(engId).observe(getViewLifecycleOwner(), englishDefs -> {
            if (englishDefs != null) {
                if (englishDefs.size() > 0) {
                    mAdapter.addPage(new EnglishTranslatedFragment());
                    chipEnglish.setVisibility(View.VISIBLE);
                    mSharedViewModel.setEngDefList(englishDefs);
                    builderEnglish = new StringBuilder();
                    for (EnglishDef englishDef : englishDefs){
                        builderEnglish.append(englishDef.getDefinition()).append("\n");
                    }
                }
            }
        });

        mInternalViewModel.getLeitnerInfoByWord(actualWord).observe(getViewLifecycleOwner(), leitner -> {
            if (leitner != null) {
                mLeitner = leitner;
                mBinding.imgBtnAddToLeitner.setImageResource(R.drawable.leitner_added);
            }
        });


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


        //button for adding word to leitner
        mBinding.imgBtnAddToLeitner.setOnClickListener(view16 -> {
            if (mLeitner != null) {
                showRemoveLeitnerDialog(view16);
            } else {
                builderTranslation = new StringBuilder();
                for (TranslationWord word : wordList) {
                    builderTranslation.append(word.getTranslatedWord()).append("\n");
                }

                //leitner id =0 means we should add brand new card
                LeitnerCardModifierBottomSheet dialog = LeitnerCardModifierBottomSheet.onNewInstance(LeitnerModifierConstants.ADD, 0);
                dialog.show(getChildFragmentManager(), "tag_dialog_add_leitner");

                String[] strings = new String[3];
                strings[0] = actualWord;
                strings[1] = builderTranslation.toString();
                if (builderEnglish != null) {
                    strings[2] = builderEnglish.toString();
                }
                mSharedViewModel.setLeitnerItemData(strings);

            }
        });

        mBinding.imgCloseToolBar.setOnClickListener(view12 -> requireActivity().getOnBackPressedDispatcher().onBackPressed());


        chipEnglish.setOnClickListener(view15 -> {
            selectEnglishChip();
            undoIdiomChip();
            undoPersianChip();
            if (isIdiomAvailable)
                mBinding.viewPagerWordInfo.setCurrentItem(2);
            else
                mBinding.viewPagerWordInfo.setCurrentItem(1);

        });
        chipIdioms.setOnClickListener(view13 -> {
            selectIdiomChip();
            undoPersianChip();
            undoEnglishChip();
            mBinding.viewPagerWordInfo.setCurrentItem(1);

        });

        chipPersian.setOnClickListener(view14 -> {
            selectPersianChip();
            undoIdiomChip();
            undoEnglishChip();
            mBinding.viewPagerWordInfo.setCurrentItem(0);

        });

        mBinding.viewPagerWordInfo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        selectPersianChip();
                        undoIdiomChip();
                        undoEnglishChip();
                        break;
                    case 1:
                        if (isIdiomAvailable) {
                            selectIdiomChip();
                            undoPersianChip();
                            undoEnglishChip();
                        } else {
                            selectEnglishChip();
                            undoIdiomChip();
                            undoPersianChip();
                        }

                        break;
                    case 2:
                        selectEnglishChip();
                        undoIdiomChip();
                        undoPersianChip();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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


    private void selectIdiomChip() {
        chipIdioms.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorSecondary)));
        chipIdioms.setTextColor(Color.WHITE);
    }

    private void selectPersianChip() {
        chipPersian.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorSecondary)));
        chipPersian.setTextColor(Color.WHITE);
    }

    private void selectEnglishChip() {
        chipEnglish.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorSecondary)));
        chipEnglish.setTextColor(Color.WHITE);

    }

    private void undoIdiomChip() {
        chipIdioms.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
        chipIdioms.setTextColor(Color.BLACK);
    }

    private void undoPersianChip() {
        chipPersian.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
        chipPersian.setTextColor(Color.BLACK);
    }

    private void undoEnglishChip() {
        chipEnglish.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.colorText)));
        chipEnglish.setTextColor(Color.BLACK);
    }

    private void showRemoveLeitnerDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Remove Word")
                .setMessage("This word exists in leitner box, do you want to remove it?")
                .setNeutralButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton("Yes", (dialogInterface, i) -> {

                    mInternalViewModel.deleteLeitnerItem(mLeitner);
                    mBinding.imgBtnAddToLeitner.setImageResource(R.drawable.leitner);
                    mLeitner = null;

                    dialogInterface.dismiss();

                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
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

        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
        Log.d(TAG, "onDestroyView: ");
    }
}
