package com.thundercandy.epq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectableCategoryAdapter extends RecyclerView.Adapter<SelectableCategoryAdapter.ViewHolder> {

    ArrayList<SelectableCategory> categories = new ArrayList<>();
    Context mContext;

    public SelectableCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectable_category, parent, false); // Creates a new view of the item that will hold each category
        return new SelectableCategoryAdapter.ViewHolder(view);                                    // Returns the ViewHolder instance of that category item
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategoryName.setText(categories.get(position).getCategoryName());
        holder.txtCardCount.setText(categories.get(position).getCardCount());
        holder.cbSelected.setSelected(categories.get(position).isSelected());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setSelectableCategories(ArrayList<SelectableCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout selectableCategory;
        TextView txtCategoryName, txtCardCount;
        CheckBox cbSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            selectableCategory = itemView.findViewById(R.id.selectableCategory);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            txtCardCount = itemView.findViewById(R.id.txtCardCount);
            cbSelected = itemView.findViewById(R.id.cbSelected);

            cbSelected.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SelectableCategory sc = categories.get(getAdapterPosition());
                if (sc != null) {
                    sc.setSelected(isChecked);
                    notifyItemChanged(getAdapterPosition());
                } else if (mContext != null) {
                    Toast.makeText(mContext, "Error selecting this category", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("CAT_SELECT_ERROR", "Error selecting category");
                }
            });
        }
    }

    public class SelectableCategory{

        private int id;
        private String categoryName;
        private int cardCount;
        private boolean selected;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getCardCount() {
            return cardCount;
        }

        public void setCardCount(int cardCount) {
            this.cardCount = cardCount;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
