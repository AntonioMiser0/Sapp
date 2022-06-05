package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {
    private ImageButton login1;
    private ImageButton register1;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login1 =  findViewById(R.id.login);
        register1 =  findViewById(R.id.register);
        login1.setOnClickListener(LoginOrRegister.this);
        register1.setOnClickListener(this);

        FirebaseAuth.AuthStateListener mAuthListener;
        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(LoginOrRegister.this, swp.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(LoginOrRegister.this, RegisterUser.class));
                break;

            case R.id.login:
                startActivity(new Intent(LoginOrRegister.this, Login.class));
        }
    }
}