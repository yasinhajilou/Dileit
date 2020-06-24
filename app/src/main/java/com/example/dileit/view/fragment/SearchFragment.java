package com.example.dileit.view.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.databinding.FragmentWordSearchBinding;
import com.example.dileit.model.entity.WordHistory;
import com.example.dileit.view.adapter.recycler.AllWordsRecyclerAdapter;
import com.example.dileit.view.viewinterface.WordsRecyclerViewInterface;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.SearchViewModel;
import com.example.dileit.viewmodel.SharedViewModel;


public class SearchFragment extends Fragment implements WordsRecyclerViewInterface {

    private AllWordsRecyclerAdapter mAdapter;
    private SharedViewModel mSharedViewModel;
    private String TAG = SearchFragment.class.getSimpleName();
    private InternalViewModel mInternalViewModel;
    private boolean isTypedYet = false;
    private FragmentWordSearchBinding mBinding;
    private SearchViewModel mSearchViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
        mSearchViewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentWordSearchBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.edtSearchWord.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(mBinding.edtSearchWord, InputMethodManager.SHOW_IMPLICIT);
        setUpRecyclerView();

        mSearchViewModel.getSyncedSearch().observe(getViewLifecycleOwner(), searchDictionaries -> {
            mAdapter.setData(searchDictionaries);
        });

        mSharedViewModel.getVoiceWord().observe(getViewLifecycleOwner(), s -> {
            if (!s.equals(""))
                mBinding.edtSearchWord.setText(s);
        });


        mBinding.btnClearEditSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.edtSearchWord.setText("");
                mBinding.btnClearEditSearch.setVisibility(View.GONE);
            }
        });

        mBinding.btnBackwardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
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
                if (!word.equals("")) {
                    mSearchViewModel.doEngSearch(word);
                    mSearchViewModel.doPerSearch(word);
                    mBinding.btnClearEditSearch.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.setData(null);
                    isTypedYet = false;
                }
            }
        });

    }

    private void setUpRecyclerView() {
        mAdapter = new AllWordsRecyclerAdapter(this::onItemClicked);
        mBinding.rvSearchRagment.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvSearchRagment.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mBinding = null;

        mSharedViewModel.resetVoiceWord();
        mSearchViewModel.reset();
    }

    @Override
    public void onItemClicked(String actualWord, int engId) {
        Bundle bundle = new Bundle();
        bundle.putString(KeysValue.KEY_BUNDLE_ACTUAL_WORD, actualWord.trim());
        bundle.putInt(KeysValue.KEY_BUNDLE_WORD_REF_ID, engId);
        WordHistory wordHistory = new WordHistory(actualWord.trim(), engId, 0, System.currentTimeMillis());
        mInternalViewModel.insertWordHistory(wordHistory);
        Navigation.findNavController(getView()).navigate(R.id.action_wordSearchFragment_to_wordInformationFragment, bundle);

    }
}
