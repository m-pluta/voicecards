package com.thundercandy.epq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.thundercandy.epq.data.Card;
import com.thundercandy.epq.data.SessionCard;
import com.thundercandy.epq.database.DbUtils;

import java.util.ArrayList;
import java.util.Collections;

public class SessionActivity extends AppCompatActivity {

    ImageView btnBack;
    SessionManager manager;
    Button btnEndSession, btnKnown, btnUnknown;
    TextView txtTerm, txtDefinition;
    ImageView btnReadTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        btnBack = findViewById(R.id.btnBack);
        btnEndSession = findViewById(R.id.btnEndSession);
        btnKnown = findViewById(R.id.btnKnown);
        btnUnknown = findViewById(R.id.btnUnknown);
        txtTerm = findViewById(R.id.txtTerm);
        txtDefinition = findViewById(R.id.txtDefinition);
        btnReadTerm = findViewById(R.id.btnReadTerm);

        overridePendingTransition(R.anim.forward_slide_in, R.anim.forward_slide_out);
        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        // This part starts the session with the categories the user selected
        Intent receivedIntent = getIntent();
        ArrayList<Integer> selectedCats = receivedIntent.getIntegerArrayListExtra("selectedCategories");
        ArrayList<SessionCard> scs = extractCardsFromCategories(selectedCats);
        Collections.shuffle(scs);
        manager = new SessionManager(this, scs);
        manager.start();

        btnEndSession.setOnClickListener(v -> {
            manager.end();
            btnKnown.setEnabled(false);
            btnUnknown.setEnabled(false);
        });
        btnKnown.setOnClickListener(v -> {
            if (manager.isEnded()) {
                btnKnown.setEnabled(false);
            } else {
                manager.known();
            }
        });
        btnUnknown.setOnClickListener(v -> {
            if (manager.isEnded()) {
                btnUnknown.setEnabled(false);
            } else {
                manager.unknown();
            }
        });
        txtDefinition.setOnClickListener(v -> {
            if (!manager.isEnded()) {
                manager.revealDefinition();
            }
        });


    }

    public ArrayList<SessionCard> extractCardsFromCategories(ArrayList<Integer> selectedCategoryIDs) {
        ArrayList<SessionCard> scs = new ArrayList<>();
        for (Integer i : selectedCategoryIDs) {
            ArrayList<Card> cards = DbUtils.fetchCategoryCards(this, i);

            for (Card c : cards) {
                scs.add(c.toSessionCard());
            }
        }

        for (SessionCard sc : scs) {
            Log.d("CARD_FETCH", sc.toString());
        }
        return scs;
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