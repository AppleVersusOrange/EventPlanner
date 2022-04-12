package com.example.eventir.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventir.R;
import com.parse.ParseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etNewUsername;
    private EditText etNewPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etNewUsername = findViewById(R.id.etNewUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etNewUsername.getText().toString();
                String password = etNewPassword.getText().toString();
                registerUser(username,password);
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void registerUser(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.put("visibility", false);
        user.put("ZIPcode", "07102");
        user.signUpInBackground(e -> {
            if (e == null) {
                Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}