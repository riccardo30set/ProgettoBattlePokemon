package com.example.progettopokeapi.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.progettopokeapi.R;

import java.util.HashMap;

public class Combat_Activity extends AppCompatActivity {
    HashMap<String,Integer>  player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_combat);
    }
    public int CalcoloHP(int base, int EV, int IV, int level){
        return (int) (Math.floor(0.01*(2*base+IV+Math.floor(0.25*EV))*level)+level+10);
    }
    public int CalcoloPureStats(int base, int EV, int IV, int level){
        return  (int) (Math.floor(0.01*(2*base+IV+Math.floor(0.25*EV))*level)+5);
    }
}