package com.example.dileit.view.fragment.wordinfo;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dileit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatedIdiomsFragment extends Fragment {


    public RelatedIdiomsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_related_idioms, container, false);
    }

}
