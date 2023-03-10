package com.example.progettopokeapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettopokeapi.partite.PartitaLocaleActivity;
import com.example.progettopokeapi.partite.PartitaOnlineActivity;
import com.example.progettopokeapi.partite.ScannerLocalConnection;
import com.example.progettopokeapi.pokedex.ListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
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
        Client.address = "pokeproject.tk";
        Client.connect(this,1500);

        String gameName = "Partita di "+Client.shared_prefs.getString("nameAccount","Giocatore");
        Client.createGame(gameName);

        Intent online = new Intent(getApplicationContext(), PartitaOnlineActivity.class);
        online.putExtra("gameName",gameName);
        online.putExtra("opponent","In attesa di un giocatore");

        Client c = new Client();
        c.start();
        startActivity(online);

        overridePendingTransition(2,2);
        overridePendingTransition(2,2);
    }

    public void onClickLocale(View view){
        Client.address = "10.10.10.191";
        Client.connect(this,1500);

        String gameName = "Partita di "+Client.shared_prefs.getString("nameAccount","Giocatore");
        Client.createGame(gameName);

        Intent online = new Intent(getApplicationContext(), PartitaOnlineActivity.class);
        online.putExtra("gameName",gameName);
        online.putExtra("opponent","In attesa di un giocatore");
        Client c = new Client();

        c.start();
        startActivity(online);

        overridePendingTransition(2,2);
        overridePendingTransition(2,2);

    }
    public void onClickPartecipa(View view){
        startActivity(new Intent(this, ScannerLocalConnection.class));
    }

}


