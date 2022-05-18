package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Lista extends AppCompatActivity implements View.OnClickListener {
    private ImageButton events;
    private Button pocetna;
    private Button profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        events=findViewById(R.id.events);
        events.setOnClickListener(Lista.this);
        pocetna=findViewById(R.id.pocetna);
        pocetna.setOnClickListener(Lista.this);
        profil=findViewById(R.id.profil);
        profil.setOnClickListener(Lista.this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.events:
                startActivity(new Intent(Lista.this, EventsActivity.class));
                break;
            case R.id.pocetna:
                startActivity(new Intent(Lista.this, swp.class));
                break;
            case R.id.profil:
                startActivity(new Intent(Lista.this, swp.class));
                break;


        }
    }
}