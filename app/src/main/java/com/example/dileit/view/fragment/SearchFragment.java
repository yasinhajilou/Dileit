package com.example.dileit.view.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.EditText;

import com.example.dileit.R;
import com.example.dileit.Utils.JsonUtils;
import com.example.dileit.view.adapter.AllWordsRecyclerAdapter;
import com.example.dileit.view.viewinterface.WordsRecyclerViewInterface;
import com.example.dileit.viewmodel.DictionaryViewModel;
import com.example.dileit.viewmodel.SharedViewModel;


public class SearchFragment extends Fragment implements WordsRecyclerViewInterface {

    private EditText edtSearch;
    private DictionaryViewModel mViewModel;
    private RecyclerView rvWords;
    private AllWordsRecyclerAdapter mAdapter;
    private SharedViewModel mSharedViewModel;
    private String TAG = SearchFragment.class.getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(DictionaryViewModel.class);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_word_search, container, false);
        edtSearch = view.findViewById(R.id.edtSearchWord);

        setUpRecyclerView(view);
        mViewModel.getAllEngWords();

        mViewModel.getData().observe(getViewLifecycleOwner(), dictionaries -> mAdapter.setData(dictionaries));

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String word = edtSearch.getText().toString();
                if (!word.equals(""))
                    mViewModel.getEngToPer(word);
                else
                    mViewModel.getAllEngWords();
            }
        });
        return view;
    }

    private void setUpRecyclerView(View view) {
        rvWords = view.findViewById(R.id.rv_search_ragment);
        mAdapter = new AllWordsRecyclerAdapter(this::onItemClicked);
        rvWords.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWords.setAdapter(mAdapter);
    }


    @Override
    public void onItemClicked(String data, String actualWord) {
        JsonUtils jsonUtils = new JsonUtils();
        mSharedViewModel.setWordInformation(jsonUtils.getWordDefinition(data));
        mSharedViewModel.setActualWord(actualWord);
        Log.d(TAG, "onItemClicked: " + jsonUtils.getWordDefinition(data));
        Navigation.findNavController(getView()).navigate(R.id.action_wordSearchFragment_to_wordInformationFragment);
    }
}
