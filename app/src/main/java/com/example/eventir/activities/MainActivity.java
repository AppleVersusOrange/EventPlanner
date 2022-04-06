package com.example.eventir.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.eventir.R;
import com.example.eventir.fragments.FeedFragment;
import com.example.eventir.fragments.ProfileFragment;
import com.example.eventir.fragments.ScheduleFragment;
import com.example.eventir.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnMain = findViewById(R.id.bnMain);
        fragmentManager.beginTransaction().replace(R.id.flContainer,new FeedFragment()).commit();
        bnMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.bnHome:
                        fragment = new FeedFragment();
                        break;
                    case R.id.bnSchedule:
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.bnProfile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.bnSettings:
                        fragment = new SettingsFragment();
                        break;
                    default:
                        fragment = new FeedFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragment).commit();
                return true;
            }
        });
    }
}