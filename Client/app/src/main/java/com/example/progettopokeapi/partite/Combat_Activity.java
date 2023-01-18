package com.example.progettopokeapi.partite;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
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
    final int critProb=24;
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

    //variabili contenenti i dati mandati dal server
    int[] dataFromServer;
    String moveOrPokemon;

    //box con nome e hpBar
    ProgressBar barPlayer;
    ProgressBar barEnemy;
    TextView boxPlayer;
    TextView boxEnemy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        imgPokemonBattlePlayer=(ImageView) findViewById(R.id.imgPokePlayer);
        imgPokemonBattleEnemy=(ImageView)findViewById(R.id.imgPokeEnemy);

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
        //
        for(int i=0;i<6;i++){
            MenuItem poke=pokemon.getItem(i);
            //recupero dati dall'intent
            team[i]=(Pokemon) getIntent().getSerializableExtra("pokemon"+(i+1));
            poke.setTitle(team[i].getNameUpperCase());
        }
        changePokemon(0);

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
                            useMove(0);
                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.move2:
                        if(!isMainPokemonKO()){
                            useMove(1);
                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.move3:
                        if(!isMainPokemonKO()){
                            useMove(2);
                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.move4:
                        if(!isMainPokemonKO()){
                            useMove(3);
                        }else{
                            blocco.setText("il tuo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon1:
                        if(team[0].getHpBattle()!=0) {
                            changePokemon(0);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon2:
                        if(team[1].getHpBattle()!=0) {
                            changePokemon(1);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon3:
                        if(team[2].getHpBattle()!=0) {
                            changePokemon(2);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon4:
                        if(team[3].getHpBattle()!=0) {
                            changePokemon(3);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon5:
                        if(team[4].getHpBattle()!=0) {
                            changePokemon(4);
                        }else {
                            blocco.setText("Questo Pokemon è KO");
                            blocco.show();
                        }
                        break;
                    case R.id.pokemon6:
                        if(team[5].getHpBattle()!=0) {
                            changePokemon(5);
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


    //selezione della mossa
    public void useMove(int numMove){
        //alert con il nome della mossa usata
        Toast toast=Toast.makeText(this, "hai usato "+team[mainPokemon].getMoveByIndex(numMove), Toast.LENGTH_SHORT);
        toast.show();
        //codice per inviare al server
        Client.makeMove(team[mainPokemon].getName(),team[mainPokemon].getHpBattle(),team[mainPokemon].getMoveByIndex(numMove));
        //aggiungere codice per ricevere dal server
        //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : FIRST|SECOND : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : PK_NAME|MV_NAME
        this.decondingDataFromServer(Client.nextLine());
        switch (dataFromServer[0]) {
            case MessageType.INCOMBAT:
                if(HasEnemyUsedMove() && hasEnemyAttackFirst()){
                    //pokemon nemico ha usato una mossa
                    enemyPokemonAttacca();
                    if(isMainPokemonKO()){
                        mandaperKO=true;
                    }else{
                        playerPokemonAttacca(enemyHpPostAction());
                        if(isMainEnemyPokemonKO()){
                            //aggiungere codice per attendere il nome del nuovo pokemon avversario
                            updateEnemyPokemon(55,"");
                        }
                    }
                }else if(HasEnemyUsedMove() && hasEnemyAttackSecond()){
                    //pokemon nemico usa una mossa e attacca per secondo
                    playerPokemonAttacca(enemyHpPostAction());
                    if(isMainEnemyPokemonKO()){
                        //aggiungere codice per attendere il nome del nuovo pokemon avversario
                        updateEnemyPokemon(55,"");
                    }else{
                        enemyPokemonAttacca();
                        if(isMainPokemonKO()){
                            mandaperKO=true;
                        }
                    }
                }else if (hasEnemyChangedPokemon()){
                    //il nemico ha cambiato pokemon
                    updateEnemyPokemon(enemyPokemonId(), moveOrPokemon);
                    playerPokemonAttacca(enemyHpPostAction());
                    if(isMainEnemyPokemonKO()){
                        //aggiungere codice per attendere il nome del nuovo pokemon avversario
                        updateEnemyPokemon(55,"");
                    }
                }
                imgMenu.setVisibility(View.VISIBLE);
                break;
            case MessageType.WIN:
                startActivity(new Intent(this, WIN_activity.class));
                break;
            case MessageType.LOSE:
                startActivity(new Intent(this, lose_activity.class));
                break;
        }

    }
    public void changePokemon(int numPokemon){
        //Comunico al server il nuovo pokemon
        Client.changePokemon(team[numPokemon].getName(),team[numPokemon].getHpBattle());
        updatePlayerPokemon(numPokemon);
        if(!mandaperKO){
            //aggiungere codice per ricevere dal server
            //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : FIRST|SECOND : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : PK_NAME|MV_NAME
            decondingDataFromServer(Client.nextLine());
            switch (dataFromServer[0]) {
                case MessageType.INCOMBAT:
                    if(HasEnemyUsedMove()){
                        enemyPokemonAttacca();
                    }else if(hasEnemyChangedPokemon()){
                        updateEnemyPokemon(enemyPokemonId(),moveOrPokemon);
                    }
                    break;
                case MessageType.LOSE:
                    startActivity(new Intent(this, lose_activity.class));
                    break;
            }
        }
        mandaperKO=false;
    }

    //inserice i dati ricevuti dal client in un array di interi e una variabile stringa, questi dati vengono poi utilizzati
    //per capire se un giocatore ha vinto, perso o è ancora in combattimento, chi attacca per primo ecc...
    //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : FIRST|SECOND : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : PK_NAME|MV_NAME
    public void decondingDataFromServer(String event){
        String[] eventElements = event.split(":");
        if(eventElements.length>1){
            dataFromServer = new int[eventElements.length - 1];
            moveOrPokemon = eventElements[eventElements.length-1];
        }else{
            dataFromServer=new int[2];
        }
        for (int i = 0; i < eventElements.length-1; i++) {
            dataFromServer[i] = Integer.parseInt(eventElements[i]);
        }
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
    //TODO Caso in cui l'avversario uccide il pokemon appena messo in campo
    public void enemyPokemonAttacca(){
        animationEnemy();
        team[mainPokemon].setHpBattle(myHpPostAction());
        updateHpPlayer();
    }
    //caricamento dati (immagine nome e hp) riguardanti il pokemon in battaglia
    public void updatePlayerPokemon(int numPokemon){
        //alert con il nome del pokemon che entra in campo
        mainPokemon=numPokemon;
        Toast toast=Toast.makeText(this, "è entrato in campo "+team[mainPokemon].getNameUpperCase(), Toast.LENGTH_SHORT);
        toast.show();
        //aggiorna gli hp
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
    public void updateEnemyPokemon(int eId, String name){
        int enemyId=getResources().getIdentifier("pokemon"+eId, "raw", getPackageName());
        imgPokemonBattleEnemy.setImageResource(enemyId);
        updateHpEnemy(enemyHpPreAction());
        boxEnemy.setText(name);
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
    //controllo se il pokemon dell'avversario è ko
    public boolean isMainEnemyPokemonKO(){
        return dataFromServer[3]==0;
    }
    //controllo se il nemico ha attaccato per primo
    public boolean hasEnemyAttackFirst(){
        return dataFromServer[2]==MessageType.FIRST;
    }
    //controllo sel il nemico ha usato una mossa
    public boolean hasEnemyAttackSecond(){
        return dataFromServer[2]==MessageType.SECOND;
    }
    //controllo sel il nemico ha usato una mossa
    public boolean HasEnemyUsedMove(){
        return dataFromServer[1]==MessageType.USED_MOVE;
    }
    //controllo sel il nemico ha cambiato pokemon
    public boolean hasEnemyChangedPokemon(){return dataFromServer[1]==MessageType.CHANGE_POKEMON;}
    //hp del nemico pre attacco
    public int enemyHpPreAction(){
        return dataFromServer[3];
    }
    //hp del nemico post attacco
    public int enemyHpPostAction(){
        return dataFromServer[4];
    }
    //hp del pokemon in campo dopo aver subito un attacco
    public int myHpPostAction(){
        return dataFromServer[5];
    }
    //id del pokemon nemico
    public int enemyPokemonId(){
        return dataFromServer[6];
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