package com.example.dileit.view.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.EnglishDef;

import java.util.List;

public class EnglishTranslationWordRecyclerAdapter extends RecyclerView.Adapter<EnglishTranslationWordRecyclerAdapter.ViewHolder> {
    List<EnglishDef> mList;

    public void setData(List<EnglishDef> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_english_translation, parent, false);
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
        TextView cat, def, syn, exam, labelExam, labelSyn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat = itemView.findViewById(R.id.tv_cat_item_eng);
            def = itemView.findViewById(R.id.tv_def_item_eng);
            syn = itemView.findViewById(R.id.tv_syn_item_eng);
            exam = itemView.findViewById(R.id.tv_example_item_eng);
            labelExam = itemView.findViewById(R.id.tv_label_example);
            labelSyn = itemView.findViewById(R.id.tv_label_synony);

        }

        void bindData(EnglishDef englishDef) {
            def.setText(englishDef.getDefinition());

            cat.setText("(" + englishDef.getCat() + ")");

            if (!englishDef.getSynonyms().equals(""))
                syn.setText(englishDef.getSynonyms());
            else
                labelSyn.setVisibility(View.GONE);

            if (!englishDef.getExamples().equals(""))
                exam.setText(englishDef.getExamples());
            else
                labelExam.setVisibility(View.GONE);
        }
    }
}
