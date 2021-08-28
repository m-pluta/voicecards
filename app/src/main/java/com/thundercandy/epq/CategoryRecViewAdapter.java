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
        holder.txtCategoryName.setText(categories.get(position).getName());

        if (categories.get(position).isExpanded()) {
            holder.imgExpandedState.setImageResource(R.drawable.ic_card_open);
            holder.parent.setBackgroundResource(R.drawable.card_header_expanded);

            holder.btnRemoveCategory.setVisibility(View.VISIBLE);
            holder.header_buffer.setVisibility(View.GONE);

            holder.btnAddNewItem.setVisibility(View.VISIBLE);
        } else {
            holder.imgExpandedState.setImageResource(R.drawable.ic_card_closed);
            holder.parent.setBackgroundResource(R.drawable.card_header_collapsed);

            holder.btnRemoveCategory.setVisibility(View.GONE);
            holder.header_buffer.setVisibility(View.VISIBLE);

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

        private CardView parent;                                         // Initializes all the GUI component variables
        private LinearLayout header;
        private ImageView imgExpandedState, btnRemoveCategory;
        private TextView txtCategoryName;
        private View header_buffer;
        private RecyclerView cardRecView;
        private LinearLayout btnAddNewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.card_parent);                 // Initializes all the GUI components
            header = itemView.findViewById(R.id.header);
            header_buffer = itemView.findViewById(R.id.header_buffer);
            imgExpandedState = itemView.findViewById(R.id.imgExpandedState);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            btnRemoveCategory = itemView.findViewById(R.id.btnRemoveCategory);
            cardRecView = itemView.findViewById(R.id.cardRecView);
            btnAddNewItem = itemView.findViewById(R.id.btnAddNewItem);

            header.setOnClickListener(v -> {
                Category c = categories.get(getAdapterPosition());
                c.flipExpanded();
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
