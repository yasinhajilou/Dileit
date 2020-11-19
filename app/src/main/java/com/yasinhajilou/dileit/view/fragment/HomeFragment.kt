package com.yasinhajilou.dileit.view.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasinhajilou.dileit.R
import com.yasinhajilou.dileit.constant.KeysValue
import com.yasinhajilou.dileit.databinding.FragmentHomeBinding
import com.yasinhajilou.dileit.model.entity.WordHistory
import com.yasinhajilou.dileit.view.adapter.recycler.WordHistoryRecyclerAdapter
import com.yasinhajilou.dileit.view.fragment.leitnercardhandler.BottomSheetAddCostumeLeitner
import com.yasinhajilou.dileit.view.viewinterface.WordsRecyclerViewInterface
import com.yasinhajilou.dileit.viewmodel.InternalViewModel
import com.yasinhajilou.dileit.viewmodel.SharedViewModel
import java.util.*

class HomeFragment : Fragment(), WordsRecyclerViewInterface {
    private var mBinding: FragmentHomeBinding? = null
    private var mViewModel: InternalViewModel? = null
    private var mAdapter: WordHistoryRecyclerAdapter? = null
    private var mSharedViewModel: SharedViewModel? = null
    private var mHomeFragmentInterface: HomeFragmentInterface? = null
    private var todayCardsSize = 0

    interface HomeFragmentInterface {
        fun onLeitnerReviewButtonTouched()
        fun onSettingMenuTouched()
        fun onLeitnerManagerMenuTouched()
        fun onReporterMenuTouched()
        fun onAboutMenuTouched()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentInterface) {
            mHomeFragmentInterface = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProvider(requireActivity()).get(InternalViewModel(requireActivity().application)::class.java)
        mSharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mAdapter = WordHistoryRecyclerAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return mBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding!!.rvWordHistory.adapter = mAdapter
        mBinding!!.rvWordHistory.layoutManager = LinearLayoutManager(view.context)
        mViewModel!!.allWordHistory.observe(viewLifecycleOwner, { wordHistories: List<WordHistory?> ->
            if (wordHistories.isNotEmpty()) {
                mBinding!!.tvWordHistoryInfo.visibility = View.GONE
                mBinding!!.rvWordHistory.visibility = View.VISIBLE
                mAdapter!!.setData(wordHistories)
            } else {
                mBinding!!.tvWordHistoryInfo.visibility = View.VISIBLE
                mBinding!!.rvWordHistory.visibility = View.GONE
            }
        })
        mViewModel!!.todayListSize.observe(viewLifecycleOwner, { size: Int ->
            todayCardsSize = size
            if (todayCardsSize <= 1) mBinding!!.tvTodayWordsHome.text = size.toString() + " " + getString(R.string.card) else mBinding!!.tvTodayWordsHome.text = size.toString() + " " + getString(R.string.cards)
        })
        mBinding!!.linearLayoutVoice.setOnClickListener { view12: View? ->
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.nees_speak))
            try {
                startActivityForResult(intent, REQ_CODE_SPEECH_TO_TEXT)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
        mBinding!!.tvHomeWord.setOnClickListener { view1: View? -> goToSearchView() }
        mBinding!!.fabAddLeitner.setOnClickListener { view1: View? ->
            val bottomSheetAddCostumeLeitner = BottomSheetAddCostumeLeitner()
            bottomSheetAddCostumeLeitner.show(childFragmentManager, "tag_dialog_costume_leitner")
        }
        mBinding!!.imgMenuBurgerHome.setOnClickListener { view13: View ->
            val popupMenu = PopupMenu(view13.context, view13)
            val menuInflater = MenuInflater(view13.context)
            menuInflater.inflate(R.menu.menu_home, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem -> onOptionsItemSelected(item) }
            popupMenu.show()
        }
        mBinding!!.btnReviewLeitner.setOnClickListener { view -> if (todayCardsSize > 0) mHomeFragmentInterface!!.onLeitnerReviewButtonTouched() else Toast.makeText(view.context, R.string.no_card_for_review, Toast.LENGTH_SHORT).show() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_action_leitner -> mHomeFragmentInterface!!.onLeitnerManagerMenuTouched()
            R.id.menu_action_setting -> mHomeFragmentInterface!!.onSettingMenuTouched()
            R.id.menu_action_reporter -> mHomeFragmentInterface!!.onReporterMenuTouched()
            R.id.menu_action_about -> mHomeFragmentInterface!!.onAboutMenuTouched()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE_SPEECH_TO_TEXT) {
            if (data != null) {
                val res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                if (res != null) {
                    mSharedViewModel!!.setVoiceWord(res[0])
                    goToSearchView()
                }
            }
        }
    }

    private fun goToSearchView() {
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_wordSearchFragment)
    }

    override fun onItemClicked(actualWord: String, engId: Int) {
        val bundle = Bundle()
        bundle.putString(KeysValue.KEY_BUNDLE_ACTUAL_WORD, actualWord.trim { it <= ' ' })
        bundle.putInt(KeysValue.KEY_BUNDLE_WORD_REF_ID, engId)
        bundle.putBoolean(KeysValue.KEY_BUNDLE_TRANSITION_HISTORY, true)
        Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_wordInformationFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    companion object {
        private const val REQ_CODE_SPEECH_TO_TEXT = 12
    }
}