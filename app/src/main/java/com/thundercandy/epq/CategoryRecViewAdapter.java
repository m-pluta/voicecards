package com.thundercandy.epq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecViewAdapter extends RecyclerView.Adapter<CategoryRecViewAdapter.ViewHolder> {

    private ArrayList<Category> categories;
    private Context mContext;

    public CategoryRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false); // Creates a new view of the CardView that will hold each category
        return new ViewHolder(view);                                    // Returns the ViewHolder instance of that CardView view
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (categories.get(position).isExpanded()) {
            holder.txtCategoryName_expanded.setText(categories.get(position).getCategory_name());

            holder.collapsedHeader.setVisibility(View.GONE);
            holder.expandedHeader.setVisibility(View.VISIBLE);
            holder.btnAddNewItem.setVisibility(View.VISIBLE);
        } else {
            holder.txtCategoryName_collapsed.setText(categories.get(position).getCategory_name());

            holder.collapsedHeader.setVisibility(View.VISIBLE);
            holder.expandedHeader.setVisibility(View.GONE);
            holder.btnAddNewItem.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;                                         // Initializes all the GUI component variable
        private LinearLayout expandedHeader, collapsedHeader;
        private ImageView btnCollapse, btnExpand, btnRemoveCategory;
        private TextView txtCategoryName_expanded, txtCategoryName_collapsed;
        private RecyclerView cardRecView;
        private LinearLayout btnAddNewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.card_parent);                 // Initializes all the GUI components
            expandedHeader = itemView.findViewById(R.id.expandedHeader);
            collapsedHeader = itemView.findViewById(R.id.collapsedHeader);
            btnCollapse = itemView.findViewById(R.id.btnCollapse);
            btnExpand = itemView.findViewById(R.id.btnExpand);
            txtCategoryName_expanded = itemView.findViewById(R.id.txtCategoryName_expanded);
            txtCategoryName_collapsed = itemView.findViewById(R.id.txtCategoryName_collapsed);
            btnRemoveCategory = itemView.findViewById(R.id.btnRemoveCategory);
            cardRecView = itemView.findViewById(R.id.cardRecView);
            btnAddNewItem = itemView.findViewById(R.id.btnAddNewItem);

            View.OnClickListener ocl = v -> {
                Category c = categories.get(getAdapterPosition());
                c.flipExpanded();
                notifyItemChanged(getAdapterPosition());
            };

            btnCollapse.setOnClickListener(ocl);
            btnExpand.setOnClickListener(ocl);

        }
    }
}
