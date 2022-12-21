package com.example.progettopokeapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettopokeapi.pokedex.ListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingActivity extends AppCompatActivity {


    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        nav=findViewById(R.id.navbarPok);

        nav.setSelectedItemId(R.id.settings);

        nav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pokedex:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Rooms:
                        Toast.makeText(getApplicationContext(),"Errore inessitente",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.settings:
                        return true;
                }

                return false;
            }
        });
    }
}