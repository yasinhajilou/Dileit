package com.example.dileit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.Idiom;
import com.example.dileit.model.IdiomInformation;

import java.util.List;

public class IdiomRecyclerAdapter extends RecyclerView.Adapter<IdiomRecyclerAdapter.ViewHolder> {

    RecyclerView.RecycledViewPool mRecycledViewPool = new RecyclerView.RecycledViewPool();
    List<Idiom> mIdiomInformation;

    public void setData(List<Idiom> data) {
        mIdiomInformation = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_idiom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mIdiomInformation != null ? mIdiomInformation.size() : 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle , tvTranslation;
        RecyclerView mRecyclerView;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_idiom_title);
            tvTranslation = itemView.findViewById(R.id.tv_idiom_translation);
            mRecyclerView = itemView.findViewById(R.id.rv_nested_example_idiom);
            mRecyclerView.setRecycledViewPool(mRecycledViewPool);

        }

        public void bindData(Idiom idiom){
             tvTitle.setText(idiom.getTitle());
        }
    }
}
