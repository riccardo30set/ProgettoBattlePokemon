package com.example.progettopokeapi.partite;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettopokeapi.Client;
import com.example.progettopokeapi.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.regex.Pattern;

public class ScannerLocalConnection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_local_connection);
        barcodeLauncher.launch(new ScanOptions());
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(ScannerLocalConnection.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {

                    String letto = result.getContents();
                    String[] dati = letto.split(":");
                    Intent play = new Intent(getApplicationContext(), PartitaOnlineActivity.class);
                    Client.address = dati[0];
                    Client.connect(this,1500);
                    play.putExtra("gameName",dati[1]);
                    Client.joinGame(dati[1]);
                    Client c = new Client();
                    c.start();
                    startActivity(play);
                }
            });
}