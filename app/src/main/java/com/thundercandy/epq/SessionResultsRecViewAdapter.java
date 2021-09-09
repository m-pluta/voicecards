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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SessionResultsRecViewAdapter extends RecyclerView.Adapter<SessionResultsRecViewAdapter.ViewHolder> {

    private ArrayList<SessionCard> cards = new ArrayList<>();
    private Context mContext;
    private double learntThreshold;

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
        int timesKnown = cards.get(position).getTimesKnown();
        int timesUnknown = cards.get(position).getTimesUnknown();
        int timesSeen = cards.get(position).getTimesSeen();

        if (timesSeen > 0) {
            holder.txtCardName.setText(cards.get(position).getTerm());
            holder.txtKnownCount.setText(String.valueOf(timesKnown));
            holder.txtKnownCount.setTextColor(mContext.getResources().getColor(R.color.results_success_color));
            holder.txtUnknownCount.setText(String.valueOf(timesUnknown));
            holder.txtUnknownCount.setTextColor(mContext.getResources().getColor(R.color.results_fail_color));
            holder.txtTotalCount.setText(String.valueOf(timesSeen));

            double percent = Math.round(((double) timesKnown / timesSeen * 100));
            DecimalFormat dFormat = new DecimalFormat();
            dFormat.setMaximumFractionDigits(0);
            holder.txtCardPercent.setText(dFormat.format(percent) + "%");

            if (percent >= (learntThreshold * 100)) {
                holder.txtCardPercent.setTextColor(mContext.getResources().getColor(R.color.results_success_color));
            } else {
                holder.txtCardPercent.setTextColor(mContext.getResources().getColor(R.color.results_fail_color));
            }
        } else {
            holder.txtCardName.setText("N/A");
            holder.txtKnownCount.setText("N/A");
            holder.txtUnknownCount.setText("N/A");
            holder.txtTotalCount.setText("N/A");

        }
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

    public void setLearntThreshold(double learntThreshold) {
        this.learntThreshold = learntThreshold;
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
