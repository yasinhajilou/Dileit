package com.example.dileit.view.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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


public class WordInformationFragment extends Fragment {

    private SharedViewModel mSharedViewModel;
    private FragmentWordInformationBinding mBinding;
    private Chip chipPersian , chipEnglish , chipIdioms;
    WordsInformationViewPagerAdapter mAdapter;

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
        mAdapter.addPage(new PersianTranslatedFragment() );
        mBinding.viewPagerWordInfo.setAdapter(mAdapter);
        mBinding.viewPagerWordInfo.setCurrentItem(0);


        mBinding.imgCloseToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();

            }
        });

        mSharedViewModel.getActualWord().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mBinding.tvWordTitle.setText(s);
            }
        });

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


}
