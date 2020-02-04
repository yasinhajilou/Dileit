package com.example.dileit.view.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.TranslationExample;

import java.util.List;

public class ExamplesRecyclerAdapter extends RecyclerView.Adapter<ExamplesRecyclerAdapter.ViewHolder> {
    List<TranslationExample> mList;

    public void setData(List<TranslationExample> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nested_examples, parent, false);
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
        TextView tvTitle, tvTranslation;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_example_title);
            tvTranslation = itemView.findViewById(R.id.tv_example_translation);
        }

        void bindData(TranslationExample example){
            tvTitle.setText(example.getSentence());
            tvTranslation.setText(example.getTranslation());
        }
    }
}
