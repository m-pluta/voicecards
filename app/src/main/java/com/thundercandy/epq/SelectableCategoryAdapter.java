package com.thundercandy.epq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thundercandy.epq.data.Category;
import com.thundercandy.epq.data.SelectableCategory;

import java.util.ArrayList;
import java.util.Collections;

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
        holder.txtCardCount.setText(String.valueOf(categories.get(position).getCardCount()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> inputCategories) {
        for (Category c : inputCategories) {
            if (c.getCards().size() != 0) {
                categories.add(c.toSelectableCategory());
            }
        }
        Collections.sort(categories);
        notifyDataSetChanged();
    }

    public ArrayList<Integer> getSelectedCategories() {
        ArrayList<Integer> out = new ArrayList<>();

        for (SelectableCategory sc : categories) {
            if (sc.isSelected()) {
                out.add(sc.getId());
            }
        }
        return out;
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
                sc.setSelected(isChecked);
                Log.d("onCheckedListener", categories.get(getAdapterPosition()).getCategoryName() + ": " + isChecked);
            });
        }
    }

}
