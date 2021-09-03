package com.thundercandy.epq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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

        // This part starts the session with the categories the user selected
        Intent receivedIntent = getIntent();
        ArrayList<Integer> selectedCats = receivedIntent.getIntegerArrayListExtra("selectedCategories");
        ArrayList<SessionCard> scs = extractCardsFromCategories(selectedCats);
        Collections.shuffle(scs);

        manager = new SessionManager(scs);

        btnEndSession.setOnClickListener(v -> {
            manager.end();
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

        btnBack.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SessionActivity.this);
            builder.setMessage("Are you sure you want to go back?\nThis will end your current session.");
            builder.setPositiveButton("Go Back", (dialog, which) -> {
                manager.end();
                onBackPressed();
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.setCancelable(true);
            builder.show();
        });

        manager.setOnEndedListener(new SessionManager.onEventListener() {
            @Override
            public void onStart() {
                btnEndSession.setEnabled(true);
                txtDefinition.setText("Click to see definition");
            }

            @Override
            public void onEnded() {
                Toast.makeText(SessionActivity.this, "Activity Ended", Toast.LENGTH_SHORT).show();
                btnKnown.setEnabled(false);
                btnUnknown.setEnabled(false);
            }

            @Override
            public void onDefinitionRevealed(String definition) {
                txtDefinition.setText(definition);
                btnKnown.setEnabled(true);
                btnUnknown.setEnabled(true);
            }

            @Override
            public void onReady(String term) {
                btnKnown.setEnabled(false);     //TODO: replace this with grey buttons or something
                btnUnknown.setEnabled(false);
                txtTerm.setText(term);
                txtDefinition.setText("Click to see definition");
            }
        });

        manager.start();


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
