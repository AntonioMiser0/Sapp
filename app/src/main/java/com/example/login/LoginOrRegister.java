package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LoginOrRegister extends AppCompatActivity implements View.OnClickListener {
    private Button login1;
    private Button register1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login1=(Button) findViewById(R.id.login);
        register1=(Button) findViewById(R.id.register);
        login1.setOnClickListener(LoginOrRegister.this);
        register1.setOnClickListener(this);
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