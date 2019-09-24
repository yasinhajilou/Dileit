package com.example.dileit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.Dictionary;
import java.util.List;

public class AllWordsRecyclerAdapter extends RecyclerView.Adapter<AllWordsRecyclerAdapter.ViewHolder> {

    private List<Dictionary> mList;

    public void setData(List<Dictionary> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcy_item_etp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         TextView tvEng,tvPer;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEng = itemView.findViewById(R.id.tv_eng_word_item);
            tvPer = itemView.findViewById(R.id.tv_per_def_item);
         }

        public void onBindData(Dictionary dictionary){
             tvEng.setText(dictionary.getEngWord());
             tvPer.setText(dictionary.getPerDefinition());
        }
    }
}
