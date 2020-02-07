package com.example.dileit.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.databinding.FragmentWordSearchBinding;
import com.example.dileit.model.WordSearch;
import com.example.dileit.utils.JsonUtils;
import com.example.dileit.view.adapter.recycler.AllWordsRecyclerAdapter;
import com.example.dileit.view.viewinterface.WordsRecyclerViewInterface;
import com.example.dileit.viewmodel.AdvancedDictionaryViewModel;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.PersianDictionaryViewModel;
import com.example.dileit.viewmodel.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class SearchFragment extends Fragment implements WordsRecyclerViewInterface {

    private PersianDictionaryViewModel mViewModel;
    private AllWordsRecyclerAdapter mAdapter;
    private SharedViewModel mSharedViewModel;
    private AdvancedDictionaryViewModel mAdvancedDictionaryViewModel;
    private String TAG = SearchFragment.class.getSimpleName();
    private InternalViewModel mInternalViewModel;
    private boolean isTypedYet = false;
    private FragmentWordSearchBinding mBinding;
    private boolean isPageStartUp = true;
    private Snackbar mSnackbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PersianDictionaryViewModel.class);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mAdvancedDictionaryViewModel = ViewModelProviders.of(this).get(AdvancedDictionaryViewModel.class);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_search, container, false);
        Log.d(TAG, "onCreateView: ");
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setUpRecyclerView(view);
        mViewModel.getAllEngWords();

        mViewModel.getData().observe(getViewLifecycleOwner(), words -> {
            mAdapter.setData(words);
            if (words.size() < 4 && isTypedYet) {
                mSnackbar = Snackbar.make(view, "Couldn't find ?", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Advanced Dictionary", view1 -> {
                            mSnackbar.dismiss();
                            mBinding.progressAdvancedDic.setVisibility(View.VISIBLE);
                            mAdvancedDictionaryViewModel.getListOfWords("72630.a019NtO4OL3oXPgGW4SzeG3eVq8uHw1Sx21lwQpk"
                                    , mBinding.edtSearchWord.getText().toString().trim(), "like", "fa2en,en2fa");
                        });
                mSnackbar.show();
            }
        });

        mSharedViewModel.getVoiceWord().observe(getViewLifecycleOwner(), s -> {
            if (!s.equals(""))
                mBinding.edtSearchWord.setText(s);
        });

        mAdvancedDictionaryViewModel.getLiveDataListOfWord().observe(getViewLifecycleOwner(), (List<WordSearch> wordSearches) -> {
            mBinding.progressAdvancedDic.setVisibility(View.GONE);
            if (wordSearches.size() > 0) {
                setUpAdvancedDic(wordSearches);
            } else {
                if (isPageStartUp) {
                    isPageStartUp = false;
                } else {
                    Toast.makeText(getContext(), "didn't find anything, Please write text correctly.", Toast.LENGTH_LONG).show();
                }
            }

        });

        mBinding.edtSearchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String word = mBinding.edtSearchWord.getText().toString();
                if (!isTypedYet) {
                    isTypedYet = true;
                }
                if (!word.equals(""))
                    mViewModel.getEngToPer(word);
                else {
                    mViewModel.getAllEngWords();
                    isTypedYet = false;
                }
            }
        });

    }

    private void setUpRecyclerView(View view) {
        RecyclerView rvWords = view.findViewById(R.id.rv_search_ragment);
        mAdapter = new AllWordsRecyclerAdapter(this::onItemClicked);
        rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWords.setAdapter(mAdapter);
    }

    private void setUpAdvancedDic(List<WordSearch> list) {
        Navigation.findNavController(getView()).navigate(R.id.action_wordSearchFragment_to_advancedSearchResultFragment);
        mSharedViewModel.setAdvancedResult(list);
        mBinding.edtSearchWord.setText("");
    }

    @Override
    public void onItemClicked(String data, String actualWord) {
        JsonUtils jsonUtils = new JsonUtils();
        mSharedViewModel.setWordInformation(jsonUtils.getWordDefinition(data));
        Bundle bundle = new Bundle();
        bundle.putString(KeysValue.KEY_BUNDLE_ACTUAL_WORD , actualWord.trim());

        Navigation.findNavController(getView()).navigate(R.id.action_wordSearchFragment_to_wordInformationFragment , bundle);
        mInternalViewModel.insertWordHistory(0, System.currentTimeMillis(), actualWord.trim(), data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdvancedDictionaryViewModel.resetListData();
        mSharedViewModel.resetVoiceWord();
        if (mSnackbar!=null){
            if (mSnackbar.isShown()){
                mSnackbar.dismiss();
            }
        }
        Log.d(TAG, "onDestroyView: called");
    }
}
