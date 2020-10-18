package com.yasinhajilou.dileit.view.adapter.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yasinhajilou.dileit.R;
import com.yasinhajilou.dileit.model.EnglishDef;

import java.util.List;

public class EnglishTranslationWordRecyclerAdapter extends RecyclerView.Adapter<EnglishTranslationWordRecyclerAdapter.ViewHolder> {
    List<EnglishDef> mList;
    private Context mContext;

    public EnglishTranslationWordRecyclerAdapter(Context context) {
        mContext = context;
    }

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
        TextView cat, def, syn, exam, labelExam, labelSyn, labelAnto, labelSimilar, similar, antonyms;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            cat = itemView.findViewById(R.id.tv_cat_item_eng);
            def = itemView.findViewById(R.id.tv_def_item_eng);
            syn = itemView.findViewById(R.id.tv_syn_item_eng);
            exam = itemView.findViewById(R.id.tv_example_item_eng);
            labelExam = itemView.findViewById(R.id.tv_label_example);
            labelSyn = itemView.findViewById(R.id.tv_label_synony);
            labelAnto = itemView.findViewById(R.id.tv_label_antonyms);
            labelSimilar = itemView.findViewById(R.id.tv_label_similar);
            similar = itemView.findViewById(R.id.tv_item_similar);
            antonyms = itemView.findViewById(R.id.tv_item_antonyms);
        }

        void bindData(EnglishDef englishDef) {
            def.setText(englishDef.getDefinition());

            String sCat = null;
            switch (englishDef.getCat()) {
                case "a":
                    sCat = mContext.getString(R.string.adjective);
                    break;
                case "v":
                    sCat = mContext.getString(R.string.verb);
                    break;
                case "r":
                    sCat = mContext.getString(R.string.adverb);
                    break;
                case "n":
                    sCat = mContext.getString(R.string.noun);
                    break;
                default:
                    sCat = englishDef.getCat();
            }

            cat.setText("(" + sCat + ")");

            if (!englishDef.getSynonyms().equals(""))
                syn.setText(englishDef.getSynonyms());
            else
                labelSyn.setVisibility(View.GONE);

            if (!englishDef.getExamples().equals(""))
                exam.setText(englishDef.getExamples());
            else
                labelExam.setVisibility(View.GONE);

            if (!englishDef.getAntonyms().equals(""))
                antonyms.setText(englishDef.getAntonyms());
            else
                labelAnto.setVisibility(View.GONE);

            if (!englishDef.getSimilar().equals(""))
                similar.setText(englishDef.getSimilar());
            else
                labelSimilar.setVisibility(View.GONE);
        }
    }
}
