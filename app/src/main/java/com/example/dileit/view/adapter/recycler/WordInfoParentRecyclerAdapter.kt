package com.example.dileit.view.adapter.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dileit.R
import com.example.dileit.model.WordInformation
import com.example.dileit.utils.WordCatSplitter

class WordInfoParentRecyclerAdapter() : RecyclerView.Adapter<WordInfoParentRecyclerAdapter.ViewHolder>() {
    private var wordInfos: List<WordInformation>? = null
    private val rvPool = RecyclerView.RecycledViewPool()
    private val TAG = "WordInfoParent"
    fun setData(wordInfos: List<WordInformation>) {
        this.wordInfos = wordInfos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_word_information, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordInfos!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(wordInfos?.get(position))
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rv: RecyclerView = itemView.findViewById(R.id.rv_parent_word_info)
        val tvCat: TextView = itemView.findViewById(R.id.tv_item_information_cat)
        fun bindData(wordInfo: WordInformation?) {
            tvCat.text = WordCatSplitter.decoratedString(wordInfo!!.type)
            val childLayoutManager = LinearLayoutManager(itemView.context)
            val childAdapter = TranslationWordRecyclerAdapter()

            rv.apply {
                adapter = childAdapter
                layoutManager = childLayoutManager
                setRecycledViewPool(rvPool)
            }
            childAdapter.setData(wordInfo.translationWords)
        }
    }
}