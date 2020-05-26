package com.example.dileit.view.adapter.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.SearchDictionary;
import com.example.dileit.view.viewinterface.WordsRecyclerViewInterface;

import java.util.List;

public class AllWordsRecyclerAdapter extends RecyclerView.Adapter<AllWordsRecyclerAdapter.ViewHolder> {

    private String TAG = AllWordsRecyclerAdapter.class.getSimpleName();
    private List<SearchDictionary> mList;
    private WordsRecyclerViewInterface mInterface;

    public AllWordsRecyclerAdapter(WordsRecyclerViewInterface anInterface) {
        mInterface = anInterface;
    }

    public void setData(List<SearchDictionary> data) {
        mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_search, parent, false);
        return new ViewHolder(view, mInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        WordsRecyclerViewInterface mInterface;

        ViewHolder(@NonNull View itemView, WordsRecyclerViewInterface anInterface) {
            super(itemView);
            mInterface = anInterface;
            tvTitle = itemView.findViewById(R.id.tv_title_word_item);
            itemView.setOnClickListener(this::onClick);
        }

        void onBindData(SearchDictionary searchDictionary) {
            tvTitle.setText(searchDictionary.getTitle());
        }

        @Override
        public void onClick(View view) {
            mInterface.onItemClicked(mList.get(getAdapterPosition()).getTitle(), mList.get(getAdapterPosition()).getEngId());
        }
    }
}
