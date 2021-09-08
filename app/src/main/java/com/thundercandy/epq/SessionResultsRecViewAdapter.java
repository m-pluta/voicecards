package com.thundercandy.epq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thundercandy.epq.data.SessionCard;

import java.util.ArrayList;

public class SessionResultsRecViewAdapter extends RecyclerView.Adapter<SessionResultsRecViewAdapter.ViewHolder> {

    private ArrayList<SessionCard> cards = new ArrayList<>();
    private Context mContext;

    public SessionResultsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_result_card, parent, false); // Creates a new view of the item that will hold each flashcard
        return new ViewHolder(view);                                    // Returns the ViewHolder instance of that CardView view
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCardName.setText(cards.get(position).getTerm());
        holder.txtKnownCount.setText(cards.get(position).getTimesKnown());
        holder.txtUnknownCount.setText(cards.get(position).getTimesUnknown());
        holder.txtTotalCount.setText(cards.get(position).getTimesSeen());

        int timesKnown = cards.get(position).getTimesKnown();
        int timesSeen = cards.get(position).getTimesSeen();
        double percent = Math.round((double) timesKnown / timesSeen * 100);
        holder.txtCardPercent.setText(String.valueOf(percent));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(ArrayList<SessionCard> cards) {
        if (cards == null) {
            cards = new ArrayList<>();
        }
        this.cards = cards;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout session_results_card;
        private TextView txtCardName, txtKnownCount, txtUnknownCount, txtTotalCount, txtCardPercent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            session_results_card = itemView.findViewById(R.id.session_results_card);    // Initializes all the GUI components
            txtCardName = itemView.findViewById(R.id.txtCardName);
            txtKnownCount = itemView.findViewById(R.id.txtKnownCount);
            txtUnknownCount = itemView.findViewById(R.id.txtUnknownCount);
            txtTotalCount = itemView.findViewById(R.id.txtTotalCount);
            txtCardPercent = itemView.findViewById(R.id.txtCardPercent);

            session_results_card.setOnClickListener(v -> {
                Toast.makeText(mContext, cards.get(getAdapterPosition()).getDefinition(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
