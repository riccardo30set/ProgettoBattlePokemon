package com.example.progettopokeapi.partite;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.progettopokeapi.Client;
import com.example.progettopokeapi.MessageType;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.WIN_activity;
import com.example.progettopokeapi.lose_activity;
import com.google.android.material.navigation.NavigationView;

public class Combat_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView combatView;
    ImageView imgMenu;
    Pokemon[] team;
    int mainPokemon;
    Boolean mandaperKO;

    //elementi del  menu
    int idPokemon;
    int idPokemonInBattle;
    ImageView imgPokemon;
    ImageView imgPokemonBattlePlayer;
    ImageView imgPokemonBattleEnemy;
    TextView txtPokemoInBattle;
    TextView hpPokemoInBattle;
    Menu moves;


    //box con nome e hpBar
    ProgressBar barPlayer;
    ProgressBar barEnemy;
    TextView boxPlayer;
    TextView boxEnemy;

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Client.gameplay = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_combat);
        team=new Pokemon[6];
        imgMenu=findViewById(R.id.imageMenu);
        mandaperKO=false;
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
        imgPokemonBattlePlayer= findViewById(R.id.imgPokePlayer);
        imgPokemonBattleEnemy=findViewById(R.id.imgPokeEnemy);

        //sottomenu mosse
        MenuItem pokemonGeneral=  general.getItem(1);
        Menu pokemon=pokemonGeneral.getSubMenu();
        //sottomenu pomkemon
        MenuItem movesGeneral=  general.getItem(0);
        moves=movesGeneral.getSubMenu();

        //box hpBar
        barPlayer=findViewById(R.id.hpBarPlayer);
        barEnemy=findViewById(R.id.hpBarEnemy);
        boxPlayer =findViewById(R.id.boxPlayerName);
        boxEnemy =findViewById(R.id.boxEnemyName);
        //thread e handler
        handler=new Handler();

        for(int i=0;i<6;i++){
            MenuItem poke=pokemon.getItem(i);
            //recupero dati dall'intent
            team[i]=(Pokemon) getIntent().getSerializableExtra("pokemon"+(i+1));
            poke.setTitle(team[i].getNameUpperCase());
            Toast.makeText(this,"vita "+team[i].getName()+" hp "+team[i].getHpBattle(),Toast.LENGTH_LONG);
        }
        Client.changePokemon(0, team);
        //alert con avvisi di blocco azione
        Toast blocco=Toast.makeText(this, "", Toast.LENGTH_SHORT);
        //gestione delle funzioni per ogni opzione nel menu
        combatView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //imgMenu.setVisibility(View.INVISIBLE);
                switch (item.getItemId()) {
                    case R.id.move1:
                        if(!isMainPokemonKO()){
                            Toast.makeText(Combat_Activity.this, "Mossa eseguita", Toast.LENGTH_SHORT).show();
                            Client.useMove(team[mainPokemon].getName(),team[mainPokemon].getMoveByIndex(0),team[mainPokemon].getHpBattle());

                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.move2:
                        if(!isMainPokemonKO()){
                            Client.useMove(team[mainPokemon].getName(),team[mainPokemon].getMoveByIndex(1),team[mainPokemon].getHpBattle());

                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.move3:
                        if(!isMainPokemonKO()){
                            Client.useMove(team[mainPokemon].getName(),team[mainPokemon].getMoveByIndex(2),team[mainPokemon].getHpBattle());

                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.move4:
                        if(!isMainPokemonKO()){
                            Client.useMove(team[mainPokemon].getName(),team[mainPokemon].getMoveByIndex(3),team[mainPokemon].getHpBattle());

                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon1:
                        if(team[0].getHpBattle()!=0) {
                            Client.changePokemon(0, team);
                            imgMenu.setVisibility(View.INVISIBLE);

                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon2:
                        if(team[1].getHpBattle()!=0) {
                            Client.changePokemon(1, team);
                            imgMenu.setVisibility(View.INVISIBLE);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon3:
                        if(team[2].getHpBattle()!=0) {
                            Client.changePokemon(2, team);
                            imgMenu.setVisibility(View.INVISIBLE);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon4:
                        if(team[3].getHpBattle()!=0) {
                            Client.changePokemon(3, team);
                            imgMenu.setVisibility(View.INVISIBLE);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon5:
                        if(team[4].getHpBattle()!=0) {
                            Client.changePokemon(4, team);
                            imgMenu.setVisibility(View.INVISIBLE);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon6:
                        if(team[5].getHpBattle()!=0) {
                            Client.changePokemon(5, team);
                            imgMenu.setVisibility(View.INVISIBLE);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
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
    public void playerPokemonAttacca(int hpEnemy){
        animationPlayer();
        updateHpEnemy(hpEnemy);
    }

    public void enemyPokemonAttacca(int myHP){
        animationEnemy();
        team[mainPokemon].setHpBattle(myHP);
        updateHpPlayer();
    }
    //caricamento dati (immagine nome e hp) riguardanti il pokemon in battaglia
    public void updatePlayerPokemon(int numPokemon, int hp){
        //alert con il nome del pokemon che entra in campo
        mainPokemon=numPokemon;
        //aggiorna gli hp
        team[numPokemon].setHpBattle(hp);
        updateHpPlayer();
        //aggiorna le mosse
        loadMoves();
        //caricamento dati header menu
        idPokemon= getResources().getIdentifier("pokemon"+team[mainPokemon].getPokedexId(), "raw", getPackageName());
        imgPokemon.setImageResource(idPokemon);
        txtPokemoInBattle.setText(team[mainPokemon].getNameUpperCase());
        hpPokemoInBattle.setText(team[mainPokemon].getHpBattle()+"/"+team[mainPokemon].getHpMax());
        //Caricamento immagine pokemon in battaglia
        idPokemonInBattle=getResources().getIdentifier("pokemonretro"+team[mainPokemon].getPokedexId(), "raw", getPackageName());
        imgPokemonBattlePlayer.setImageResource(idPokemonInBattle);
        //cambio nome nel box
        boxPlayer.setText(team[mainPokemon].getNameUpperCase());

    }

    //caricamento dati (immagine nome e hp) riguardanti il pokemon in battaglia del nemico
    public void updateEnemyPokemon(int eId, String name,int hp){
        int enemyId=getResources().getIdentifier("pokemon"+eId, "raw", getPackageName());
        imgPokemonBattleEnemy.setImageResource(enemyId);
        updateHpEnemy(hp);
        boxEnemy.setText((name.charAt(0)+"").toUpperCase()+name.substring(1));
    }

    //apertura del menu
    public void onClick(View view){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    //movimento fatto da un pokemon dell'utente quando essa seleziona la mossa
    public void animationPlayer(){
        TranslateAnimation animation = new TranslateAnimation(0, 680, 0, -200);
        animation.setDuration(1500);
        imgPokemonBattlePlayer.startAnimation(animation);
    }

    //movimento fatto da un pokemon dell'utente seleziona la mossa
    public void animationEnemy(){
        TranslateAnimation animation = new TranslateAnimation(0, -680, 0, 200);
        animation.setDuration(1500);
        imgPokemonBattleEnemy.startAnimation(animation);
    }
    //aggiorna sulla progress bar gli hp del pokemon in campo
    public void updateHpPlayer(){
        int perc=team[mainPokemon].getHpBattle()*100/team[mainPokemon].getHpMax();
        ObjectAnimator animation = ObjectAnimator.ofInt(barPlayer, "progress", barPlayer.getProgress(), perc);
        animation.setDuration(2000); // 2 seconds
        animation.start();
        if(perc<50 && perc>20){
            ColorStateList colorStateList = ColorStateList.valueOf(Color.YELLOW);
            barPlayer.setProgressTintList(colorStateList);
        }else if(perc<20){
            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
            barPlayer.setProgressTintList(colorStateList);
        }else{
            barPlayer.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        }
    }

    //aggiorna sulla progress bar gli hp del pokemon avversario in campo
    public void updateHpEnemy(int newHp){
        ObjectAnimator animation = ObjectAnimator.ofInt(barEnemy, "progress", barEnemy.getProgress(), newHp);
        animation.setDuration(2000); // 2 seconds
        animation.start();
        if(newHp<50 && newHp>20){
            ColorStateList colorStateList = ColorStateList.valueOf(Color.YELLOW);
            barEnemy.setProgressTintList(colorStateList);
        }else if(newHp<20){
            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
            barEnemy.setProgressTintList(colorStateList);
        }else{
            barEnemy.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#03DAC5")));
        }
    }
    //controllo se il pokemon del player è ko
    public boolean isMainPokemonKO(){
        return team[mainPokemon].getHpBattle()==0;
    }
    public int getMainPokemon(){
        return mainPokemon;
    }
    public void menuVisible(){
        imgMenu.setVisibility(View.VISIBLE);
    }


    /*
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
    */
}