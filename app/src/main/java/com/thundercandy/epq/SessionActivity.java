package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Intent receivedIntent = getIntent();

        ArrayList<Integer> selectedCats = receivedIntent.getIntegerArrayListExtra("selectedCategories");

    }

    public ArrayList<Card> extractCardsFromCategories(ArrayList<Integer> selectedCategoryIDs) {
        ArrayList<Card> cards = new ArrayList<>();


        return cards;
    }
}
