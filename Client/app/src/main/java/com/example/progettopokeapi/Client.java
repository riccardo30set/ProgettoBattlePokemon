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
    Socket server;
    PrintWriter socketOut;
    Scanner socketIn;
    SharedPreferences shared_prefs;
    /**
     *Creates a tcp socket to the given address and port and
     * creates a PrintWriter and Scanner object to handle the I/O streams
     */
    public void connect(Context context, InetAddress address, int port){
        try{
            server = new Socket(address, port);
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
    private void sendPlayerInfo(Context context){
        shared_prefs=context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        if(!shared_prefs.getString("nameAccount","").equals("")){
            socketOut.println(2);
            socketOut.println(shared_prefs.getString("nameAccount",""));
            socketOut.println("bonoooo");
        }
    }


}
