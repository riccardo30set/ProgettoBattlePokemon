package com.example.progettopokeapi.partite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettopokeapi.MainActivity;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Random;
import java.util.zip.Inflater;

public class Combat_Activity extends AppCompatActivity {
    final int critProb=24;
    DrawerLayout drawerLayout;
    NavigationView combatView;
    Pokemon[] team;
    //elementi del header menu
    int mainPokemon;
    int idPokemon;
    int idPokemonInBattle;
    ImageView imgPokemon;
    ImageView imgPokemonBattlePlayer;
    ImageView imgPokemonBattleEnemy;
    TextView txtPokemoInBattle;
    TextView hpPokemoInBattle;
    Menu moves;
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
        imgPokemon=HeaderMenu.findViewById(R.id.imgBattlePokemon);
        txtPokemoInBattle= HeaderMenu.findViewById(R.id.txtPokemonInBattle);
        hpPokemoInBattle=HeaderMenu.findViewById(R.id.hpPokemonInBattle);
        
        //immagini pokemon in campo
        imgPokemonBattlePlayer=findViewById(R.id.imgPokePlayer);
        imgPokemonBattleEnemy=findViewById(R.id.imgPokeEnemy);
        
        //sottomenu mosse
        MenuItem pokemonGeneral=  general.getItem(1);
        Menu pokemon=pokemonGeneral.getSubMenu();
        //sottomenu pomkemon
        MenuItem movesGeneral=  general.getItem(0);
        moves=movesGeneral.getSubMenu();

        for(int i=0;i<6;i++){
            MenuItem poke=pokemon.getItem(i);
            //recupero dati dall'intent
            team[i]=(Pokemon) getIntent().getSerializableExtra("pokemon"+(i+1));
            poke.setTitle(team[i].getNameUpperCase());
        }
        changePokemon(0);
        loadMoves();

        //gestione delle funzioni per ogni opzione nel menu
        combatView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.move1:
                        useMove(0);
                        break;
                    case R.id.move2:
                        useMove(1);
                        break;
                    case R.id.move3:
                        useMove(2);
                        break;
                    case R.id.move4:
                        useMove(3);
                        break;
                    case R.id.pokemon1:
                        changePokemon(0);
                        break;
                    case R.id.pokemon2:
                        changePokemon(1);
                        break;
                    case R.id.pokemon3:
                        changePokemon(2);
                        break;
                    case R.id.pokemon4:
                        changePokemon(3);
                        break;
                    case R.id.pokemon5:
                        changePokemon(4);
                        break;
                    case R.id.pokemon6:
                        changePokemon(5);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }
    
    
    //selezione della mossa
    public void useMove(int numMove){
        //alert con il nome della mossa usata
        Toast toast=Toast.makeText(this, "hai usato "+team[mainPokemon].getMoveByIndex(numMove), Toast.LENGTH_SHORT);
        toast.show();
        //aggiungere codice per l'animazione
        //aggiugere codice per inviare al server
    }
    
    
    //caricamento dati (immagine nome e hp) riguardanti il pokemon in battaglia
    public void changePokemon(int numPokemon){
        //alert con il nome della mossa usata
        mainPokemon=numPokemon;
        Toast toast=Toast.makeText(this, "è entrato in campo "+team[mainPokemon].getNameUpperCase(), Toast.LENGTH_SHORT);
        toast.show();
        //caricamento dati header menu
        idPokemon= getResources().getIdentifier("pokemon"+team[mainPokemon].getPokedexId(), "raw", getPackageName());
        imgPokemon.setImageResource(idPokemon);
        txtPokemoInBattle.setText(team[mainPokemon].getNameUpperCase());
        hpPokemoInBattle.setText(team[mainPokemon].getHpBattle()+"/"+team[mainPokemon].getHpMax());
        //Caricamento immagine pokemon in battaglia
        idPokemonInBattle=getResources().getIdentifier("pokemonretro"+team[mainPokemon].getPokedexId(), "raw", getPackageName());
        imgPokemonBattlePlayer.setImageResource(idPokemonInBattle);
        loadMoves();
        //aggiugere codice per inviare al server
    }

    public void loadMoves(){
        //cambio delle mosse
        for(int i=0;i<4;i++){
            MenuItem move=moves.getItem(i);
            String nameMove=team[mainPokemon].getMoveByIndex(i);
            //caricamento nome e tipo(rappresentato da una icona) della mossa
            move.setTitle(nameMove);
            move.setIcon(getResources().getIdentifier("type"+team[mainPokemon].getTypeByName(nameMove),"drawable",getPackageName()));
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