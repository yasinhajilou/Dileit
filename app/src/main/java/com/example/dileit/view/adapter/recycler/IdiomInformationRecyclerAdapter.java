package com.example.dileit.view.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.IdiomExample;

import java.util.List;

public class IdiomInformationRecyclerAdapter extends RecyclerView.Adapter<IdiomInformationRecyclerAdapter.ViewHolder> {

    private List<IdiomExample> mList;

    public void setData(List<IdiomExample> data) {
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
        return mList!= null ? mList.size() :0;
    }


     class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvExample,tvTranslation;
         ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExample = itemView.findViewById(R.id.tv_example_title);
            tvTranslation = itemView.findViewById(R.id.tv_example_translation);
        }

        void bindData(IdiomExample idiomExample){
             tvExample.setText(idiomExample.getExample());
             tvTranslation.setText(idiomExample.getExampleTranslation());
        }
    }
}
