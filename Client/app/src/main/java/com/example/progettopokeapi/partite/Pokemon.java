package com.example.progettopokeapi.partite;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pokemon implements Serializable {
    private String name;
    private HashMap<String,String> moves;

    public Pokemon(String name,HashMap <String,String>  moves) {
        this.name = name;
        this.moves=moves;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMoveByIndex(int index){
        String name="";
        int count=0;
        for (String temp: moves.keySet()) {
            if(count==index){
                name=temp;
            }
            count++;
        }
        return name;
    }
    public String getTypeByIndex(int index){
        String name="";
        int count=0;
        for (String temp: moves.values()) {
            if(count==index){
                name=temp;
            }
            count++;
        }
        return name;
    }
    public String getTypeByName(String move){
        return moves.get(move);
    }
}
