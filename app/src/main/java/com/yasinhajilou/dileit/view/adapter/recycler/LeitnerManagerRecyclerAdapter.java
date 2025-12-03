package com.yasinhajilou.dileit.view.adapter.recycler;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yasinhajilou.dileit.R;
import com.yasinhajilou.dileit.model.entity.Leitner;

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

    public void setData(List<Leitner> data) {
        mLeitners = data;
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextView;
        ImageView imgEdit, imgDelete;
        private Leitner currentLeitner;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_item_leitner_manager);
            imgDelete = itemView.findViewById(R.id.img_remove_leitner_manager_item);
            imgEdit = itemView.findViewById(R.id.img_edit_leitner_manager_item);
            itemView.setOnClickListener(this);
            
            // Set click listeners once in constructor to avoid memory leaks
            imgEdit.setOnClickListener(view -> {
                if (currentLeitner != null) {
                    mInterface.onEditSelected(currentLeitner);
                }
            });

            imgDelete.setOnClickListener(view -> {
                if (currentLeitner != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                            .setTitle(R.string.delete_leitner_title)
                            .setMessage(R.string.delete_leitner_caption)
                            .setNeutralButton(R.string.yes, (dialogInterface, i) -> {
                                int position = getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    Leitner leitnerToDelete = currentLeitner;
                                    mLeitners.remove(leitnerToDelete);
                                    notifyItemRemoved(position);
                                    mInterface.onDeleteSelected(leitnerToDelete);
                                }
                            })
                            .setNegativeButton(R.string.no, (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            });
                    builder.show();
                }
            });
        }

        void bindData(Leitner leitner) {
            currentLeitner = leitner;
            mTextView.setText(leitner.getWord());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Toast.makeText(view.getContext(), mLeitners.get(position).getState() + "", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


