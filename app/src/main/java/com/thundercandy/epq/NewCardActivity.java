package com.thundercandy.epq;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.thundercandy.epq.database.DbUtils;
import com.thundercandy.epq.events.CardAddedEvent;

import org.greenrobot.eventbus.EventBus;

public class NewCardActivity extends AppCompatActivity {

    private Button btnFinish;
    private EditText txtTerm, txtDefinition;

    private static int targetCategoryID = -1;
    private static int targetCategoryPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        btnFinish = findViewById(R.id.btnFinish);
        txtTerm = findViewById(R.id.txtTerm);
        txtDefinition = findViewById(R.id.txtDefinition);

        Intent intent = getIntent();
        targetCategoryID = intent.getIntExtra("targetCategoryID", -1);
        targetCategoryPosition = intent.getIntExtra("targetCategoryPosition", 0);


        btnFinish.setOnClickListener(v -> {
            String inputTerm = txtTerm.getText().toString();
            String inputDefinition = txtDefinition.getText().toString();

            if (inputTerm.length() > 0 && inputDefinition.length() > 0 && targetCategoryID != -1) {
                //TODO: Make sure to pass an actual date instead of 0
                int card_id = DbUtils.addCard(this, targetCategoryID, inputTerm, inputDefinition, 0);
                Log.d("DB_ADD", "ID: "+ card_id + ", Term: " + inputTerm + " added to DB");
                CardAddedEvent event = new CardAddedEvent();
                event.setCategory(targetCategoryID);
                event.setAddedCard(new Card(card_id, inputTerm, inputDefinition));
                event.setTargetPosition(targetCategoryPosition);
                Log.d("DB_ADD", "Event Pre-fire");
                EventBus.getDefault().post(event);
                Log.d("DB_ADD", "Event Fired");

                finish();
            }
        });
    }
}
