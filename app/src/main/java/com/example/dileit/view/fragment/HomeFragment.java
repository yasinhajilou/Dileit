package com.example.dileit.view.fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment {
    private static final int REQ_CODE_SPEECH_TO_TEXT = 12;
    private FragmentHomeBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.linearLayoutVoice.setOnClickListener(view12 -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_TO_TEXT);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.txtShowLieItem.setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_wordSearchFragment);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_TO_TEXT:
                if (data != null) {
                    ArrayList res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (res != null) {
                        Toast.makeText(getContext(), res.get(0).toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "is null", Toast.LENGTH_SHORT).show();

                    }
                }
                break;

        }
    }
}
