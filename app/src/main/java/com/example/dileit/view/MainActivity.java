package com.example.dileit.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.dileit.R;


public class MainActivity extends AppCompatActivity {

    EditText edtWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtWord = findViewById(R.id.edtWord);
    }
}
