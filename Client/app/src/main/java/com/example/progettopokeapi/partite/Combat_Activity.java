package com.example.progettopokeapi.partite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progettopokeapi.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;

public class Combat_Activity extends AppCompatActivity {
    final int critProb=24;
    DrawerLayout drawerLayout;
    NavigationView combatView;
    Pokemon[] team;
    //elementi del header menu
    int pokemonInBattle;
    int idPokemonInBattle;
    ImageView imgPokemonInBattle;
    TextView txtPokemoInBattle;
    TextView hpPokemoInBattle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_combat);
        team=new Pokemon[6];
        //apertura del menu
        drawerLayout=findViewById(R.id.drawerLayout);
        //colori delle icone del menu
        combatView=findViewById(R.id.navigationView);
        combatView.setItemIconTintList(null);

        //elementi del header menu
        Menu general=combatView.getMenu();
        View HeaderMenu=combatView.getHeaderView(0);
        imgPokemonInBattle=HeaderMenu.findViewById(R.id.imgBattlePokemon);
        txtPokemoInBattle= HeaderMenu.findViewById(R.id.txtPokemonInBattle);
        hpPokemoInBattle=HeaderMenu.findViewById(R.id.hpPokemonInBattle);
        //inserimento nomi dei pokemon nel menu
        MenuItem pokemonGeneral=  general.getItem(1);
        Menu pokemon=pokemonGeneral.getSubMenu();
        for(int i=0;i<6;i++){
            MenuItem poke=pokemon.getItem(i);
            //recupero dati dall'intent
            team[i]=(Pokemon) getIntent().getSerializableExtra("pokemon"+(i+1));
            poke.setTitle(team[i].getName());
        }
        //caricamento dati (immagine nome e hp) riguardanti il pokemon in battaglia (mostrato nel header menu)
        idPokemonInBattle= getResources().getIdentifier("pokemon3", "raw", getPackageName());
        imgPokemonInBattle.setImageResource(idPokemonInBattle);
        txtPokemoInBattle.setText(team[pokemonInBattle].getName());
        hpPokemoInBattle.setText("355/355");
        //inserimento nomi delle mosse nel menu
        MenuItem movesGeneral=  general.getItem(0);
        Menu moves=movesGeneral.getSubMenu();
        for(int i=0;i<4;i++){
            MenuItem move=moves.getItem(i);
            String nameMove=team[pokemonInBattle].getMoveByIndex(i);
            //caricamento nome e tipo(rappresentato da una icona) della mossa
            move.setTitle(nameMove);
            move.setIcon(getResources().getIdentifier("type"+team[pokemonInBattle].getTypeByName(nameMove),"drawable",getPackageName()));
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