package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instagramclone.Fragment.HomeFragment;
import com.example.instagramclone.Fragment.NotificationFragment;
import com.example.instagramclone.Fragment.ProfileFragment;
import com.example.instagramclone.Fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavigationBarView navigationBarView;
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedSelector);
/*
        bottomNavigationView.setOnItemSelectedListener(bottomNavigationView.OnItemSelectedListener());
        NavigationBarView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener());
        navigationBarView.setOnItemSelectedListener(navigationItemSelectedSelector);*/
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();


    }
    /*private NavigationBarView.OnItemSelectedListener navigationItemSelectedSelector = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = null;
                startActivity(new Intent(MainActivity.this, PostActivity.class));
            } else if (itemId == R.id.nav_heart) {
                selectedFragment = new NotificationFragment();
            } else if (itemId == R.id.nav_profile) {
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profileID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                editor.apply();
                selectedFragment = new ProfileFragment();
            }
            if (selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            }
            return true;
        }
    };*/
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedSelector = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_search) {
                selectedFragment = new SearchFragment();
            } else if (itemId == R.id.nav_add) {
                selectedFragment = null;
                startActivity(new Intent(MainActivity.this, PostActivity.class));
            } else if (itemId == R.id.nav_heart) {
                selectedFragment = new NotificationFragment();
            } else if (itemId == R.id.nav_profile) {
                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                editor.putString("profileID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                editor.apply();
                selectedFragment = new ProfileFragment();
            }
            if (selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            }
            return true;
        }
    };
}