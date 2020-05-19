package com.example.dileit.view.adapter.recycler;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dileit.R;
import com.example.dileit.model.entity.Leitner;

import java.util.List;

public class LeitnerManagerRecyclerAdapter extends RecyclerView.Adapter<LeitnerManagerRecyclerAdapter.ViewHolder> {

    private LeitnerManagerInterface mInterface;
    private List<Leitner> mLeitners;

    public interface LeitnerManagerInterface {
        void onDeleteSelected(Leitner leitner);

        void onEditSelected(Leitner leitner);
    }

    public LeitnerManagerRecyclerAdapter(LeitnerManagerInterface anInterface) {
        mInterface = anInterface;

    }

    public void setData(List<Leitner> data, boolean notifyRv) {
        mLeitners = data;
        if (notifyRv)
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_leitner_manager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mLeitners.get(position));
    }

    @Override
    public int getItemCount() {
        return mLeitners != null ? mLeitners.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView imgEdit, imgDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_leitner_manager);
            imgDelete = itemView.findViewById(R.id.img_remove_leitner_manager_item);
            imgEdit = itemView.findViewById(R.id.img_edit_leitner_manager_item);
        }

        void bindData(Leitner leitner) {
            mTextView.setText(leitner.getWord());

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onEditSelected(leitner);
                }
            });


            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete Leitner Card")
                            .setMessage("Do you want to delete this leitner card?")
                            .setNeutralButton("Yes", (dialogInterface, i) -> {
                                mLeitners.remove(leitner);
                                notifyItemRemoved(getAdapterPosition());
                                mInterface.onDeleteSelected(leitner);
                            })
                            .setNegativeButton("No", (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            });
                    builder.show();

                }
            });
        }
    }
}


