package com.example.dileit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.dileit.model.Word;
import com.example.dileit.viewmodel.DictionaryViewModel;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    DictionaryViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mViewModel = ViewModelProviders.of(this).get(DictionaryViewModel.class);

        mViewModel.getAllEngWords();

    }
}
