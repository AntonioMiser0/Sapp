package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Profil extends AppCompatActivity implements View.OnClickListener {
private ImageButton  uredivanje;
private ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        uredivanje= findViewById(R.id.uredivanje);
        uredivanje.setOnClickListener(Profil.this);
        button= findViewById(R.id.imageButton);
        button.setOnClickListener(Profil.this);


    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.uredivanje:
                startActivity(new Intent(Profil.this, Uredivanje.class));
                break;
            case R.id.imageButton:
                startActivity(new Intent(Profil.this, swp.class));
                break;


        }
    }
}