package com.example.dileit.view.adapter.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.Idiom;
import com.example.dileit.model.IdiomExample;

import java.util.ArrayList;
import java.util.List;

public class IdiomRecyclerAdapter extends RecyclerView.Adapter<IdiomRecyclerAdapter.ViewHolder> {

    private static final String TAG = IdiomRecyclerAdapter.class.getSimpleName();
    private RecyclerView.RecycledViewPool mRecycledViewPool = new RecyclerView.RecycledViewPool();
    private List<Idiom> mList;

    public void setData(List<Idiom> data) {
        mList = data;
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
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTranslation;
        RecyclerView mRecyclerView;
        IdiomInformationRecyclerAdapter adapter;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_idiom_title);
            tvTranslation = itemView.findViewById(R.id.tv_idiom_translation);
            mRecyclerView = itemView.findViewById(R.id.rv_nested_example_idiom);
            mRecyclerView.setRecycledViewPool(mRecycledViewPool);

            adapter = new IdiomInformationRecyclerAdapter();
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

        void bindData(Idiom idiom) {
             List<IdiomExample> mIdiomExamples = new ArrayList<>();
            tvTitle.setText(idiom.getTitle());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < idiom.getIdiomInformation().size(); i++) {
                stringBuilder.append(idiom.getIdiomInformation().get(i).getIdiomTranslation()).append(".");
                if (idiom.getIdiomInformation().get(i).getIdiomExamples() != null)
                    mIdiomExamples.addAll(idiom.getIdiomInformation().get(i).getIdiomExamples());
            }
            tvTranslation.setText(stringBuilder.toString());
            adapter.setData(mIdiomExamples);
        }
    }
}
