package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.thundercandy.epq.data.Card;
import com.thundercandy.epq.database.DbUtils;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity {

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        btnBack = findViewById(R.id.btnBack);

        overridePendingTransition(R.anim.forward_slide_in, R.anim.forward_slide_out);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        Intent receivedIntent = getIntent();
        ArrayList<Integer> selectedCats = receivedIntent.getIntegerArrayListExtra("selectedCategories");

        extractCardsFromCategories(selectedCats);

    }

    public ArrayList<Card> extractCardsFromCategories(ArrayList<Integer> selectedCategoryIDs) {
        ArrayList<Card> cards = new ArrayList<>();
        for (Integer i : selectedCategoryIDs) {
            cards.addAll(DbUtils.fetchCategoryCards(this, i));
        }

        for (Card c : cards) {
            Log.d("CARD_FETCH", c.toString());
        }
        return cards;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
    }

}
