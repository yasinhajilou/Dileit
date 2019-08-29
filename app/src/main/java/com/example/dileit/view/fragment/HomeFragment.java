package com.example.dileit.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dileit.R;


public class HomeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        CardView cardView = view.findViewById(R.id.card_search_home);
        TextView textView = view.findViewById(R.id.tvHomeWord);

        textView.setOnClickListener(view1 -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(cardView, getContext().getResources().getString(R.string.transition_card_fragment))
                        .build();

                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_wordSearchFragment
                        , null
                        , null
                        , extras);

            } else {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_wordSearchFragment);
            }
        });
        return view;
    }



}
