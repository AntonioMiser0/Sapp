package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profil extends AppCompatActivity implements View.OnClickListener {
private Button  uredivanje;
private Button pocetna;
    private Button lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        uredivanje= findViewById(R.id.uredivanje);
        uredivanje.setOnClickListener(Profil.this);
        pocetna= findViewById(R.id.pocetna);
        pocetna.setOnClickListener(Profil.this);
        lista= findViewById(R.id.lista);
        lista.setOnClickListener(Profil.this);

    }
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.uredivanje:
                startActivity(new Intent(Profil.this, Uredivanje.class));
                break;
            case R.id.pocetna:
                startActivity(new Intent(Profil.this, swp.class));
                break;

            case R.id.lista:
                startActivity(new Intent(Profil.this, Lista.class));
                break;


        }
    }
}