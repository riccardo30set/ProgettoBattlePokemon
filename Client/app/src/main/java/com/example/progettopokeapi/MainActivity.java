package com.example.progettopokeapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettopokeapi.partite.PartitaLocaleActivity;
import com.example.progettopokeapi.partite.PartitaOnlineActivity;
import com.example.progettopokeapi.pokedex.ListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav =findViewById(R.id.navbarPok);

        nav.setSelectedItemId(R.id.home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.home:

                        break;
                    case R.id.pokedex:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(2,2);
                        break;
                    case R.id.Rooms:
                        startActivity(new Intent(getApplicationContext(), Combat_Activity.class));
                        overridePendingTransition(2,2);
                        break;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(2,2);
                        break;

                }

                return true;

            }
        });
    }

    public void onClickOnline(View view){
        startActivity(new Intent(getApplicationContext(), PartitaOnlineActivity.class));
        overridePendingTransition(2,2);
    }

    public void onClickLocale(View view){
        startActivity(new Intent(getApplicationContext(), PartitaLocaleActivity.class));
        overridePendingTransition(2,2);

    }
    public void onClickPartecipa(View view){

    }
}


