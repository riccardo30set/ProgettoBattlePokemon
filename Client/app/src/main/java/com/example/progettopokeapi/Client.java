package com.example.progettopokeapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.progettopokeapi.partite.Combat_Activity;
import com.example.progettopokeapi.partite.PartitaOnlineActivity;
import com.example.progettopokeapi.partite.Pokemon;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    static Socket server;
    static PrintWriter socketOut;
    static Scanner socketIn;
    static SharedPreferences shared_prefs;
    static TextView opponent;
    static Context gameContext;
    static Intent gameIntent;
    public static Combat_Activity gameplay;
    int [] responseInt;
    String [] responseWords;
    public static String address;
    /**
     *Creates a tcp socket to the given address and port and
     * creates a PrintWriter and Scanner object to handle the I/O streams
     */
    public static void connect(Context context, int port){
        try{
            server = new Socket(InetAddress.getByName(address), port);
            socketOut = new PrintWriter(server.getOutputStream(), true);
            socketIn = new Scanner(server.getInputStream());
            sendPlayerInfo(context);
        }catch(IOException e){
            Toast.makeText(context,"Cannot create socket or get I/O streams",Toast.LENGTH_LONG).show();
        }

    }


    /**
     * Sends username and description to the server
     * @param context
     */
    private static void sendPlayerInfo(Context context){
        shared_prefs=context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        if(!shared_prefs.getString("nameAccount","").equals("")){
            socketOut.println(2);
            socketOut.println(shared_prefs.getString("nameAccount",""));
            socketOut.println("bonoooo");
        }
    }

    public static void createGame(String name){
        socketOut.println(MessageType.CREATE_GAME);
        socketOut.println(name);

    }

    /**
     * Joins the player to the game with the specified name
     * @param name
     * @return the opponent's name
     */
    public static String joinGame(String name){
        socketOut.println(MessageType.JOIN_GAME);
        socketOut.println(name);
        socketOut.println(MessageType.GET_OPPONENT_IDENTITY);
        String nome = socketIn.nextLine();
        socketIn.nextLine();
        return nome;
    }

    public static void setReady(Boolean state, Intent game, Context context){
        gameContext = context;
        gameIntent = game;
        socketOut.println(MessageType.SET_READY);
        socketOut.println(state);

    }

    public void run(){
        while(true)
            switch(Integer.parseInt(socketIn.nextLine())){
                case MessageType.GUEST_JOINED:
                    //setOpponentName(socketIn.nextLine());
                    socketIn.nextLine();
                    break;
                case MessageType.GAME_STARTED:
                    gameContext.startActivity(gameIntent);
                    break;
                case MessageType.ACTION:
                    String response=socketIn.nextLine();
                    splitResponse(response);
                    //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : CHANGEPK|U_MOVE : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : MV_NAME : PK_NAME
                    gameplay.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(responseInt[2]==MessageType.USED_MOVE)
                                gameplay.animationPlayer();
                            if(responseInt[1]==MessageType.USED_MOVE)
                                gameplay.animationEnemy();
                            gameplay.updateEnemyPokemon(responseInt[6],responseWords[1],responseInt[4]);
                            gameplay.updatePlayerPokemon(gameplay.getMainPokemon(),responseInt[5]);
                            if(responseInt[5]==0 || (responseInt[5]!=0 && responseInt[4]!=0)){
                                gameplay.menuVisible();
                            }
                            if(responseInt[0]==MessageType.WIN){
                                gameplay.startActivity(new Intent(gameContext,WIN_activity.class));
                            }else if (responseInt[0]==MessageType.LOSE){
                                gameplay.startActivity(new Intent(gameContext,lose_activity.class));
                            }
                        }
                    });

                    break;
                case MessageType.ONLY_ONE_ACT:
                    String responseOnly=socketIn.nextLine();
                    splitResponse(responseOnly);
                    gameplay.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gameplay.updateEnemyPokemon(responseInt[6],responseWords[1],responseInt[4]);
                            gameplay.menuVisible();
                        }
                    });
            }
    }


    private void setOpponentName(String name){
        opponent.setText(name);
    }


    public static void useMove(String pokemon,String mossa,int currentHP){
        socketOut.println(MessageType.ACTION);
        socketOut.println(MessageType.USED_MOVE+":"+pokemon+":"+currentHP+":"+mossa);
    }

    public static void changePokemon(int pokemon, Pokemon[] team){
        if(team[gameplay.getMainPokemon()].getHpBattle()==0){
            socketOut.println(MessageType.ONLY_ONE_ACT);
        }else{
            socketOut.println(MessageType.ACTION);
        }
        socketOut.println(MessageType.CHANGE_POKEMON+":"+team[pokemon].getName()+":"+team[pokemon].getHpBattle());
        gameplay.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(team[gameplay.getMainPokemon()].getHpBattle()==0){
                    gameplay.menuVisible();
                }
                gameplay.updatePlayerPokemon(pokemon,team[pokemon].getHpBattle());

            }
        });
    }

    //inserice i dati ricevuti dal client in un array di interi e una variabile stringa, questi dati vengono poi utilizzati
    //per capire se un giocatore ha vinto, perso o è ancora in combattimento, chi attacca per primo ecc...
    //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : CHANGEPK|U_MOVE : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : MV_NAME : PK_NAME
    public void splitResponse(String event){
        String[] eventElements = event.split(":");
        responseInt = new int[eventElements.length - 2];
        responseWords = new String[2];
        for (int i = 0; i < eventElements.length; i++) {
            if(i<eventElements.length-2){
                responseInt[i] = Integer.parseInt(eventElements[i]);
            }else{
                responseWords[i-(eventElements.length-2)]=eventElements[i];
            }
        }

    }

    public static void disconnect(){
        System.out.println("si");
    }
}
