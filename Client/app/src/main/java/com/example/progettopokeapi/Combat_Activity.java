package com.example.progettopokeapi;

import static java.util.Map.entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.progettopokeapi.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Combat_Activity extends AppCompatActivity {
    List<Map<String, Integer>> pokemonsPlayer = new ArrayList<Map<String, Integer>>();
    final int critProb=24;
    DrawerLayout drawerLayout;
    NavigationView combatView;
    //List<Map<String, Integer>> pokemonsEnemy = new ArrayList<Map<String, Integer>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_combat);
        //apertura del menu
        drawerLayout=findViewById(R.id.drawerLayout);
        //colori delle icone del menu
        combatView=findViewById(R.id.navigationView);
        combatView.setItemIconTintList(null);
        //calcolo delle statistiche di un pokemon
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
            }};
            pokemonsEnemy.add(pokemonPlayer);*/
        }


        //inserimento nomi delle mosse
        Menu general=combatView.getMenu();
        MenuItem movesGeneral=  general.getItem(0);
        Menu moves=movesGeneral.getSubMenu();
        for(int i=0;i<4;i++){
            MenuItem move=moves.getItem(i);
            //qui aggiungere il codice per ottenere il nome della mossa
            move.setTitle("mossaprova");
        }
        //inserimento nomi dei pokemon
        MenuItem pokemonGeneral=  general.getItem(1);
        Menu pokemon=pokemonGeneral.getSubMenu();
        for(int i=0;i<6;i++){
            MenuItem poke=pokemon.getItem(i);
            //qui aggiungere il codice per ottenere il nome del pokemon
            poke.setTitle("pokemonprova");
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