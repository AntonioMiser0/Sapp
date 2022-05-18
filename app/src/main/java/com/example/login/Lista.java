package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Lista extends AppCompatActivity implements View.OnClickListener {
    private Button events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        events=(Button) findViewById(R.id.events);
        events.setOnClickListener(Lista.this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.events:
                startActivity(new Intent(Lista.this, EventsActivity.class));
                break;

        }
    }
}