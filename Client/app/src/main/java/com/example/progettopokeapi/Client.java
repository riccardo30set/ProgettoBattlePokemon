package com.example.progettopokeapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progettopokeapi.partite.PartitaOnlineActivity;

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
    /**
     *Creates a tcp socket to the given address and port and
     * creates a PrintWriter and Scanner object to handle the I/O streams
     */
    public static void connect(Context context, String address, int port){
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

    public static void createGame(String name,TextView opponent){
        socketOut.println(MessageType.CREATE_GAME);
        socketOut.println(name);

    }

    /**
     * Joins the player to the game with the specified name
     * @param name
     * @return the opponent's name
     */
    public static String joinGame(String name,TextView opponentName){
        socketOut.println(MessageType.JOIN_GAME);
        socketOut.println(name);
        socketOut.println(MessageType.GET_OPPONENT_IDENTITY);
        String nome = socketIn.nextLine();
        opponent = opponentName;
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
        while(true){
            switch(Integer.parseInt(socketIn.nextLine())){
                case MessageType.GUEST_JOINED:
                    //setOpponentName(socketIn.nextLine());
                    socketIn.nextLine();
                    break;
                case MessageType.GAME_STARTED:
                    gameContext.startActivity(gameIntent);
                    break;
            }
        }
    }

    public static void changePokemon(String name, int hp){
        socketOut.println(MessageType.ACTION);
        socketOut.println(MessageType.CHANGE_POKEMON+":"+name+":"+hp);
    }

    private void setOpponentName(String name){
        opponent.setText(name);
    }

    public static void makeMove(String pokemonName,int hp, String move){
        socketOut.println(MessageType.ACTION);
        socketOut.println(MessageType.USED_MOVE+":"+pokemonName+":"+hp+":"+move);
    }

    public static String nextLine(){
        return socketIn.nextLine();
    }


}
