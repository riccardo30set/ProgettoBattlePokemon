package com.example.progettopokeapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Socket server;
    static PrintWriter socketOut;
    static Scanner socketIn;
    static SharedPreferences shared_prefs;
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

    public static void createGame(String name){
        socketOut.println(MessageType.CREATE_GAME);
        socketOut.println(name);
    }

    public static void setReady(Boolean state){
        socketOut.println(MessageType.SET_READY);
        socketOut.println(state);
    }


}
