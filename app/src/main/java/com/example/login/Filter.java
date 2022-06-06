package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Filter extends AppCompatActivity {
Button netflix,zabava,citanje;
    String filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        netflix=findViewById(R.id.button1);
        zabava=findViewById(R.id.button2);
        citanje=findViewById(R.id.button3);
        Intent in=new Intent(Filter.this,swp.class);

    netflix.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filter="Netflix";
            in.putExtra("fil",filter);
            startActivity(in);
        }
    });
zabava.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filter="Zabava";
            in.putExtra("fil",filter);
            startActivity(new Intent(Filter.this,swp.class));
        }
    });

citanje.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            filter="Citanje";
            in.putExtra("fil",filter);
            startActivity(new Intent(Filter.this,swp.class));
        }
    });
}}