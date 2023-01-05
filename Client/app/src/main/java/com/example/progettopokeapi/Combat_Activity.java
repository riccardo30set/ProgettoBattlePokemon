package com.example.progettopokeapi;

import static java.util.Map.entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.progettopokeapi.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Combat_Activity extends AppCompatActivity {
    List<Map<String, Integer>> pokemonsPlayer = new ArrayList<Map<String, Integer>>();
    final int critProb=24;
    DrawerLayout drawerLayout;
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
            drawerLayout=findViewById(R.id.drawerLayout);
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
    public int calcoloHP(int base, int EV, int IV, int level){
        return (int) (Math.floor(0.01*(2*base+IV+Math.floor(0.25*EV))*level)+level+10);
    }
    public int calcoloPureStats(int base, int EV, int IV, int level){
        return  (int) (Math.floor(0.01*(2*base+IV+Math.floor(0.25*EV))*level)+5);
    }
    public int moveDamage(int power, int ATK, int DEF, int level,String moveType, String[] pokemonTypes){
        double moltiplicatore=1;
        //STAB
        for(int i=0;i<pokemonTypes.length;i++){
            if(pokemonTypes[i].equals(moveType))
                moltiplicatore*=1.5;
        }
        //CRIT
        Random rand=new Random();
        if(rand.nextInt(24)==0)
            moltiplicatore*=1.5;
        moltiplicatore*= (((double)rand.nextInt(15)/100.0)+0.85);
        return (int) (((((2*level/5)+2)*power*(ATK/DEF)/50)+2)*moltiplicatore);
    }
    public void onClick(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }
}