package com.example.readil_legal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.readil_legal.fragment.FavoriteFragment;
import com.example.readil_legal.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;

            if (menuItem.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
//                } else if (menuItem.getItemId() == R.id.nav_search) {
//                    selectedFragment = new SearchFragment();
            } else if (menuItem.getItemId() == R.id.nav_favorite) {
                selectedFragment = new FavoriteFragment();
//                } else if (menuItem.getItemId() == R.id.nav_profile) {
//                    selectedFragment = new ProfileFragment();
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