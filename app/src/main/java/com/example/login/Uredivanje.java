package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Uredivanje extends AppCompatActivity implements View.OnClickListener {
private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uredivanje);
        back= findViewById(R.id.back);
        back.setOnClickListener(Uredivanje.this);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(Uredivanje.this, Profil.class));
                break;
         }
    }
    }