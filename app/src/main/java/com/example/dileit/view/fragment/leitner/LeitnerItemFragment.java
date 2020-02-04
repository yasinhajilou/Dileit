package com.example.dileit.view.fragment.leitner;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeitnerItemFragment extends Fragment {


    public LeitnerItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leitner_item, container, false);
    }

}
