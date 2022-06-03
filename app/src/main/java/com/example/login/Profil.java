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
        postavke= findViewById(R.id.postavke);
        postavke.setOnClickListener(this);
        natrag=findViewById(R.id.natrag);
        natrag.setOnClickListener(this);

    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.uredivanje:
               startActivity(new Intent(Profil.this, Uredivanje.class));
                break;
            case R.id.natrag:
                startActivity(new Intent(Profil.this, swp.class));
                break;


        }
    }
}