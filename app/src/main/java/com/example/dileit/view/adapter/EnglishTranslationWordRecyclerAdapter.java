package com.example.dileit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.WordEnglishDic;

import java.util.List;

public class EnglishTranslationWordRecyclerAdapter extends RecyclerView.Adapter<EnglishTranslationWordRecyclerAdapter.ViewHolder> {
    List<WordEnglishDic> mList;

    public void setData(List<WordEnglishDic> data){
        mList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_english_translation , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_english_translation);
        }

        void bindData(WordEnglishDic wordEnglishDic){
            mTextView.setText(wordEnglishDic.getDefinition());
        }
    }
}
