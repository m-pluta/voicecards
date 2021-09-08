package com.thundercandy.epq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thundercandy.epq.data.SessionCard;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SessionResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_results);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<SessionCard>>() {}.getType();

        Intent receivedIntent = getIntent();
        ArrayList<SessionCard> cards = gson.fromJson(receivedIntent.getStringExtra("SessionCards"), type);
    }
}
