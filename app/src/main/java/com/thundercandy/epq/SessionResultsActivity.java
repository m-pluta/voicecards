package com.thundercandy.epq;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thundercandy.epq.data.SessionCard;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SessionResultsActivity extends AppCompatActivity {

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_results);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SessionCard>>() {
        }.getType();

        Intent receivedIntent = getIntent();
        ArrayList<SessionCard> cards = gson.fromJson(receivedIntent.getStringExtra("SessionCards"), type);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.back_slide_in, R.anim.back_slide_out);
        super.onBackPressed();
    }


}
