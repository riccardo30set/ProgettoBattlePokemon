package com.example.progettopokeapi.partite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettopokeapi.Client;
import com.example.progettopokeapi.R;

public class Partecipa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partecipa);
    }

    public void join(View view){
        TextView gameName = (TextView)findViewById(R.id.joinGameInput);
        Intent play = new Intent(getApplicationContext(), PartitaOnlineActivity.class);
        play.putExtra("gameName",gameName.getText().toString());
        play.putExtra("opponent",Client.joinGame(gameName.getText().toString(),findViewById(R.id.opponentName)));
        Client c = new Client();
        c.start();
        startActivity(play);
    }


}