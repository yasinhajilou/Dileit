package com.example.dileit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.WordSearch;

import java.util.List;

public class AdvancedDicItemsRecyclerAdapter extends RecyclerView.Adapter<AdvancedDicItemsRecyclerAdapter.ViewHolder> {

    private List<WordSearch> mWordSearches;

    public void setData(List<WordSearch> data){
        mWordSearches = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_advanced_dic , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mWordSearches.get(position));
    }

    @Override
    public int getItemCount() {
        return mWordSearches!=null ? mWordSearches.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle,tvTranslation;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_advanced_dic_title);
            tvTranslation = itemView.findViewById(R.id.tv_advanced_dic_translation);
        }

        void bindData(WordSearch wordSearch){
             if (!wordSearch.getEnglishTitle().equals("")){
                 tvTitle.setText(wordSearch.getEnglishTitle());
             }
             if (!wordSearch.getPersianTitle().equals("")){
                 tvTitle.setText(wordSearch.getPersianTitle());
             }

             tvTranslation.setText(wordSearch.getTranslation());
        }
    }
}
