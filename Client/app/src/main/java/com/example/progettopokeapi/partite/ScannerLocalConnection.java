package com.example.progettopokeapi.partite;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.progettopokeapi.R;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

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
                    Toast.makeText(ScannerLocalConnection.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                    try {
                        String wifiString = result.getContents();
                        String[] wifiData = wifiString.split(";");
                        String ssid = wifiData[0].substring(7);
                        String password = wifiData[2].substring(2);
                        Log.d("--ssid",ssid);
                        Log.d("--password",password);
                        //verificare se è un qrcode wifi
                        //richiesta di connessione a internet

                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiConfiguration wifiConfig = new WifiConfiguration();
                        wifiConfig.SSID =String.format("\"%s\"", ssid);//ssid;
                        wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                        wifiConfig.preSharedKey = String.format("\"%s\"",password);//password;
                        int netId = wifiManager.addNetwork(wifiConfig);
                        Log.d("--netId", String.valueOf(netId));

                        wifiManager.enableNetwork(netId, true);
                        wifiManager.reconnect();
                    }catch(Exception e) {

                    }
                }
            });
}