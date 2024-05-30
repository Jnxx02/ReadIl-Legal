package com.example.final_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.final_mobile.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private Button btnMainLogin, btnMainRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMainLogin = findViewById(R.id.btn_main_login);
        btnMainRegister = findViewById(R.id.btn_main_register);

        btnMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(toLoginActivity);
            }
        });

        btnMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegisterActivity = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(toRegisterActivity);
            }
        });

        preferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        if (preferences.getBoolean("is_logged_in", false)) {
            Intent toHomeActivity = new Intent(this, HomeActivity.class);
            startActivity(toHomeActivity);
            finish();
        }
    }
}