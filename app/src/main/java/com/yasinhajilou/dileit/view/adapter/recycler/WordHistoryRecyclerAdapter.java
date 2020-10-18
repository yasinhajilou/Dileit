package com.yasinhajilou.dileit.view.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yasinhajilou.dileit.R;
import com.yasinhajilou.dileit.model.entity.WordHistory;
import com.yasinhajilou.dileit.view.viewinterface.WordsRecyclerViewInterface;

import java.util.List;

public class WordHistoryRecyclerAdapter extends RecyclerView.Adapter<WordHistoryRecyclerAdapter.ViewHolder> {
    private List<WordHistory> mList;
    private WordsRecyclerViewInterface mOnItemClickListener;

    public WordHistoryRecyclerAdapter(WordsRecyclerViewInterface onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<WordHistory> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_word_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_word_history_title);
            itemView.setOnClickListener(this::onClick);
        }

        void bind(WordHistory wordHistory) {
            tvTitle.setText(wordHistory.getWord());
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClicked(mList.get(getAdapterPosition()).getWord() , mList.get(getAdapterPosition()).getEngId());
        }
    }
}
