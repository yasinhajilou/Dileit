package com.example.dileit.view.fragment.leitner;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
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
    private boolean isPageStartUp;
    private boolean isHeaderOpen;


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


        mBinding.layoutContainerReview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int currentRepeat = mLeitner.getRepeatCounter();
                mLeitner.setRepeatCounter(++currentRepeat);
                mLeitner.setLastReviewTime(System.currentTimeMillis());
                showSecondView((int) motionEvent.getX(), (int) motionEvent.getY());

                return false;
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
                if (mLeitner.getState() == LeitnerStateConstant.STARTED)
                    mLeitner.setState(LeitnerStateConstant.BOX_ONE);

                mInternalViewModel.updateLeitnerItem(mLeitner);
                mListener.onNoClicked();
            }
        });

//        mBinding.tvBritishPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.onBritishPronounce(mWord);
//            }
//        });
//
//        mBinding.tvAmericanPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.onAmericanPronounce(mWord);
//            }
//        });
//
//        mBinding.imgAmericanPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.onAmericanPronounce(mWord);
//            }
//        });
//
//        mBinding.imgBritishPronounceLeitnerRev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mListener.onBritishPronounce(mWord);
//            }
//        });
    }


    //Handle view visibility witch circular animation
    private void showSecondView(int x, int y) {

        //start from center
        int startRadius = 0;

        // get the final radius for the clipping circle which is layoutMain whole size
        int finalRadius = Math.max(mBinding.layoutContainerReview.getWidth(), mBinding.layoutContainerReview.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            anim = ViewAnimationUtils.createCircularReveal(mBinding.layoutSecondReview, x, y, startRadius, finalRadius);
        }


        // make the view visible and start the animation
        mBinding.layoutSecondReview.setVisibility(View.VISIBLE);


        anim.start();

        isHeaderOpen = true;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }


}
