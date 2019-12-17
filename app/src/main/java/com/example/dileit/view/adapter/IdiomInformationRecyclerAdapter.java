package com.example.dileit.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.IdiomInformation;

import java.util.List;

public class IdiomInformationRecyclerAdapter extends RecyclerView.Adapter<IdiomInformationRecyclerAdapter.ViewHolder> {

    RecyclerView.RecycledViewPool mRecycledViewPool = new RecyclerView.RecycledViewPool();
    List<IdiomInformation> mIdiomInformation;

    public void setData(List<IdiomInformation> data) {
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
         ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
