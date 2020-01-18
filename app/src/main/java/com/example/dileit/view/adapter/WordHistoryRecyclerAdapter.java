package com.example.dileit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.Word;
import com.example.dileit.model.entity.WordHistory;

import java.util.List;

public class WordHistoryRecyclerAdapter extends RecyclerView.Adapter<WordHistoryRecyclerAdapter.ViewHolder> {
    private List<WordHistory> mList;

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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvInformation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInformation = itemView.findViewById(R.id.tv_word_history_translation);
            tvTitle = itemView.findViewById(R.id.tv_word_history_title);
        }

        void bind(WordHistory wordHistory){
            tvInformation.setText(wordHistory.getWordDef());
            tvTitle.setText(wordHistory.getWord());
        }
    }
}
