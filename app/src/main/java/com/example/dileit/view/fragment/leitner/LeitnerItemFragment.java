package com.example.dileit.view.fragment.leitner;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dileit.R;
import com.example.dileit.constant.KeysValue;
import com.example.dileit.constant.LeitnerStateConstant;
import com.example.dileit.databinding.FragmentLeitnerItemBinding;
import com.example.dileit.model.entity.Leitner;
import com.example.dileit.utils.LeitnerUtils;
import com.example.dileit.view.adapter.viewpager.LeitnerItemTranslationViewPagerAdapter;
import com.example.dileit.viewmodel.InternalViewModel;

import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.List;


public class LeitnerItemFragment extends Fragment {
    private final String TAG = LeitnerItemFragment.class.getSimpleName();
    private FragmentLeitnerItemBinding mBinding;
    private int listId;
    private InternalViewModel mInternalViewModel;
    private InterfaceReviewButtonClickListener mListener;
    private Leitner mLeitner;
    private String mWord;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceReviewButtonClickListener) {
            mListener = (InterfaceReviewButtonClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternalViewModel = ViewModelProviders.of(getActivity()).get(InternalViewModel.class);
        if (getArguments() != null)
            listId = getArguments().getInt(KeysValue.KEY_BUNDLE_LEITNER_ITEM_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLeitnerItemBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInternalViewModel.getAllLeitnerItems().observe(getViewLifecycleOwner(), new Observer<List<Leitner>>() {
            @Override
            public void onChanged(List<Leitner> leitnerList) {
                for (Leitner leitner :
                        leitnerList) {
                    if (leitner.getId() == listId) {
                        mWord = leitner.getWord();
                        mBinding.tvWordTitleReviewLeitner.setText(mWord);
                        List<String> strings = new ArrayList<>();
                        strings.add(leitner.getDef());
                        if (leitner.getSecondDef() != null)
                            strings.add(leitner.getSecondDef());
                        mBinding.viewPagerLeitnerItemTranslation.setAdapter(new LeitnerItemTranslationViewPagerAdapter(view.getContext(), strings));
                        mLeitner = leitner;
                        break;
                    }

                }
            }
        });


        mBinding.layoutFirstReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBinding.layoutFirstReview.setVisibility(View.GONE);
                mBinding.layoutSecondReview.setVisibility(View.VISIBLE);
                int currentRepeat = mLeitner.getRepeatCounter();
                mLeitner.setRepeatCounter(++currentRepeat);
                mLeitner.setLastReviewTime(System.currentTimeMillis());
            }
        });


        mBinding.btnReviewYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextBox = LeitnerUtils.nextBoxFinder(mLeitner.getState());
                if (nextBox != -1)
                    mLeitner.setState(nextBox);
                else
                    Toast.makeText(view.getContext(), "An error occurred", Toast.LENGTH_SHORT).show();

                mInternalViewModel.updateLeitnerItem(mLeitner);
                mListener.onYesClicked();
            }
        });

        mBinding.btnReviewNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInternalViewModel.updateLeitnerItem(mLeitner);
                mListener.onNoClicked();
            }
        });

        mBinding.tvBritishPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBritishPronounce(mWord);
            }
        });

        mBinding.tvAmericanPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAmericanPronounce(mWord);
            }
        });

        mBinding.imgAmericanPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAmericanPronounce(mWord);
            }
        });

        mBinding.imgBritishPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onBritishPronounce(mWord);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}
