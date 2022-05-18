package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profil extends AppCompatActivity implements View.OnClickListener {
private Button  uredivanje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        uredivanje= findViewById(R.id.uredivanje);
        uredivanje.setOnClickListener(Profil.this);
    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.uredivanje:
                startActivity(new Intent(Profil.this, Uredivanje.class));
                break;
        }
    }
}