package com.example.leafy;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ActivityResultLauncher permissionLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(navigationItemSelectedListener);
        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->
        {
            if(!isGranted)
            {
                Log.v("Camera Permission","Permission No Granted");
            }
        });
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.CAMERA);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container_view,FeedFragment.newInstance()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            CharSequence text="feeds";
            switch (item.getItemId())
            {

                case R.id.menu_bottom_nav_item_scan: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,ScanFragment.newInstance()).commit();
                    break;
                case R.id.menu_bottom_nav_item_status: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view,StatusFragment.newInstance()).commit();
                    break;
                case R.id.menu_bottom_nav_item_feed: getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, FeedFragment.newInstance()).commit();

            }

            Log.v("MENU Item","Clicked"+item.getItemId());

            return true;
        }
    };

    }



