package com.example.dileit.view.adapter.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dileit.R
import com.example.dileit.model.Contributor
import com.google.android.material.chip.Chip

class ContributorsRecyclerAdapter : RecyclerView.Adapter<ContributorsRecyclerAdapter.ViewHolder>() {

    private var mList: List<Contributor>? = null

    public fun setData(list: List<Contributor>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_contibutor, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mList?.get(position))
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val chip: Chip = view.findViewById(R.id.chip_contributor)

        fun bindData(contributor: Contributor?) {
            if (contributor != null)
                chip.text = contributor.username
        }
    }
}