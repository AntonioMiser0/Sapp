package com.example.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class Lista extends AppCompatActivity implements  View.OnClickListener {
    ImageButton events;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        events = findViewById(R.id.events);
        events.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.events:
                startActivity(new Intent(this, EventsActivity.class));
                break;
            case R.id.pocetna:
                startActivity(new Intent(this, swp.class));
                break;
        }
    }
}