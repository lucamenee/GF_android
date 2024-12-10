package com.example.gf_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.LoginResponse;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {

            EditText editText = findViewById(R.id.username);
            username = editText.getText().toString();;
            EditText editText2 = findViewById(R.id.password);
            password = editText2.getText().toString();

            LoginResponse lr = Api.login(username, password);

            if (lr != null) {
                startActivity(new Intent(this, MainActivity.class).putExtra("id_utente", lr.id_utente).putExtra( "id_inventario", lr.id_inventario));;
                finish();
            } else {
                //wrong credentials
                Log.i("LoginActivity", "wrong credentials");
            }
        });

        Button registerButton = findViewById(R.id.registrationButton);
        registerButton.setOnClickListener( v -> { startActivity(new Intent(this, RegistrationActivity.class)); finish(); });
    }



}
