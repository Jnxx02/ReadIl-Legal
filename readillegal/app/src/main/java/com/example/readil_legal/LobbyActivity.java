package com.example.readil_legal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.readil_legal.fragment.FavoriteFragment;
import com.example.readil_legal.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LobbyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;

            if (menuItem.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (menuItem.getItemId() == R.id.nav_lobby) {
                Intent intent = new Intent(LobbyActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (menuItem.getItemId() == R.id.nav_favorite) {
                selectedFragment = new FavoriteFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null)
                        .commit();
            }

            return true;
        });

        getSupportFragmentManager().

                beginTransaction()
                        .

                replace(R.id.fragment_container, new HomeFragment())
                        .

                commit();
    }
}