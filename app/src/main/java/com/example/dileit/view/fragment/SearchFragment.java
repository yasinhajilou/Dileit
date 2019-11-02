package com.example.dileit.view.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.model.WordDefinition;
import com.example.dileit.view.adapter.AllWordsRecyclerAdapter;
import com.example.dileit.view.viewinterface.WordsRecyclerViewInterface;
import com.example.dileit.viewmodel.DictionaryViewModel;
import com.google.gson.Gson;


public class SearchFragment extends Fragment implements WordsRecyclerViewInterface {
    private EditText edtSearch;
    private DictionaryViewModel mViewModel;
    private RecyclerView rvWords;
    private AllWordsRecyclerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DictionaryViewModel.class);

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
                String word = edtSearch.getText().toString();
                if (!word.equals(""))
                    mViewModel.getEngToPer(word);
                else
                    mViewModel.getAllEngWords();
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
    public void onItemClicked(String data) {
        Gson gson = new Gson();
        WordDefinition[] info = gson.fromJson(data , WordDefinition[].class);

    }
}
