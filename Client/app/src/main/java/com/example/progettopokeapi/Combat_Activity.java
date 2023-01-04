package com.example.progettopokeapi;

import static java.util.Map.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.progettopokeapi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Combat_Activity extends AppCompatActivity {
    List<Map<String, Integer>> pokemonsPlayer = new ArrayList<Map<String, Integer>>();
    //List<Map<String, Integer>> pokemonsEnemy = new ArrayList<Map<String, Integer>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_combat);
        for(int i=0;i<6;i++){
            Map<String, Integer> pokemonPlayer = new HashMap<String, Integer>() {{
                put("HP", 0);
                put("ATK", 0);
                put("DEF", 0);
                put("SPATK", 0);
                put("SPDEF", 0);
                put("SPEED", 0);
            }};
            pokemonsPlayer.add(pokemonPlayer);
            /*Map<String, Integer> pokemonEnemy = new HashMap<String, Integer>() {{
                put("HP", 0);
                put("ATK", 0);
                put("DEF", 0);
                put("SPATK", 0);
                put("SPDEF", 0);
                put("SPEED", 0);
            }};*/
            //pokemonsEnemy.add(pokemonPlayer);
        }
    }
    public int CalcoloHP(int base, int EV, int IV, int level){
        return (int) (Math.floor(0.01*(2*base+IV+Math.floor(0.25*EV))*level)+level+10);
    }
    public int CalcoloPureStats(int base, int EV, int IV, int level){
        return  (int) (Math.floor(0.01*(2*base+IV+Math.floor(0.25*EV))*level)+5);
    }

}