package com.example.dileit.view.fragment;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.databinding.FragmentHomeBinding;
import com.example.dileit.view.adapter.recycler.WordHistoryRecyclerAdapter;
import com.example.dileit.view.fragment.leitnercardhandler.BottomSheetAddCostumeLeitner;
import com.example.dileit.view.viewinterface.WordsRecyclerViewInterface;
import com.example.dileit.viewmodel.InternalViewModel;
import com.example.dileit.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.Locale;


public class HomeFragment extends Fragment implements WordsRecyclerViewInterface {
    private static final int REQ_CODE_SPEECH_TO_TEXT = 12;
    private FragmentHomeBinding mBinding;
    private InternalViewModel mViewModel;
    private WordHistoryRecyclerAdapter mAdapter;
    private SharedViewModel mSharedViewModel;

    private HomeFragmentInterface mHomeFragmentInterface;

    private int todayCardsSize;

    public interface HomeFragmentInterface {
        void onLeitnerReviewButtonTouched();

        void onSettingMenuTouched();

        void onLeitnerManagerMenuTouched();

        void onReporterMenuTouched();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentInterface) {
            mHomeFragmentInterface = (HomeFragmentInterface) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mAdapter = new WordHistoryRecyclerAdapter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.rvWordHistory.setAdapter(mAdapter);
        mBinding.rvWordHistory.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mViewModel.getAllWordHistory().observe(getViewLifecycleOwner(), wordHistories -> {
            if (wordHistories.size() > 0) {
                mBinding.tvWordHistoryInfo.setVisibility(View.GONE);
                mBinding.rvWordHistory.setVisibility(View.VISIBLE);
                mAdapter.setData(wordHistories);
            } else {
                mBinding.tvWordHistoryInfo.setVisibility(View.VISIBLE);
                mBinding.rvWordHistory.setVisibility(View.GONE);
            }
        });

        mViewModel.getTodayListSize().observe(getViewLifecycleOwner(), size -> {
            todayCardsSize = size;
            if (todayCardsSize <= 1)
                mBinding.tvTodayWordsHome.setText(size + getString(R.string.card));
            else
                mBinding.tvTodayWordsHome.setText(size + getString(R.string.cards));
        });

        mBinding.linearLayoutVoice.setOnClickListener(view12 -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.nees_speak));
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_TO_TEXT);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.tvHomeWord.setOnClickListener(view1 -> goToSearchView());

        mBinding.fabAddLeitner.setOnClickListener(view1 -> {
            BottomSheetAddCostumeLeitner bottomSheetAddCostumeLeitner = new BottomSheetAddCostumeLeitner();
            bottomSheetAddCostumeLeitner.show(getChildFragmentManager(), "tag_dialog_costume_leitner");
        });

        mBinding.imgMenuBurgerHome.setOnClickListener(view13 -> {
            PopupMenu popupMenu = new PopupMenu(view13.getContext(), view13);
            MenuInflater menuInflater = new MenuInflater(view13.getContext());
            menuInflater.inflate(R.menu.menu_home, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(HomeFragment.this::onOptionsItemSelected);
            popupMenu.show();
        });

        mBinding.btnReviewLeitner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (todayCardsSize > 0)
                    mHomeFragmentInterface.onLeitnerReviewButtonTouched();
                else
                    Toast.makeText(view.getContext(), R.string.no_card_for_review, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_leitner:
                mHomeFragmentInterface.onLeitnerManagerMenuTouched();
                break;
            case R.id.menu_action_setting:
                mHomeFragmentInterface.onSettingMenuTouched();
                break;
            case R.id.menu_action_reporter:
                mHomeFragmentInterface.onReporterMenuTouched();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH_TO_TEXT) {
            if (data != null) {
                ArrayList<String> res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (res != null) {
                    mSharedViewModel.setVoiceWord(res.get(0));
                    goToSearchView();
                }
            }
        }
    }

    private void goToSearchView() {
        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_wordSearchFragment);
    }

    @Override
    public void onItemClicked(String actualWord, int engId) {
        Bundle bundle = new Bundle();
        bundle.putString(KeysValue.KEY_BUNDLE_ACTUAL_WORD, actualWord.trim());
        bundle.putInt(KeysValue.KEY_BUNDLE_WORD_REF_ID, engId);
        bundle.putBoolean(KeysValue.KEY_BUNDLE_TRANSITION_HISTORY,true );
        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_wordInformationFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }


}
