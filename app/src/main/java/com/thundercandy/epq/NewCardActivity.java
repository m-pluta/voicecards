package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.thundercandy.epq.database.DbUtils;

public class NewCardActivity extends AppCompatActivity {

    private Button btnFinish;
    private EditText txtTerm, txtDefinition;

    private static int targetCategoryID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);

        btnFinish = findViewById(R.id.btnFinish);
        txtTerm = findViewById(R.id.txtTerm);
        txtDefinition = findViewById(R.id.txtDefinition);

        Intent intent = getIntent();
        targetCategoryID = intent.getIntExtra("targetCategoryID", -1);

        btnFinish.setOnClickListener(v -> {
            String inputTerm = txtTerm.getText().toString();
            String inputDefinition = txtDefinition.getText().toString();

            if (inputTerm.length() > 0 && inputDefinition.length() > 0 && targetCategoryID != -1) {
                //TODO: Make sure to pass an actual date instead of 0
                DbUtils.addCard(this, targetCategoryID, inputTerm, inputDefinition, 0);
                onBackPressed();
            }
        });
    }
}
