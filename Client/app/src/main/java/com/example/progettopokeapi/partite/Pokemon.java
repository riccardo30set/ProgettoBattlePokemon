package com.example.progettopokeapi.partite;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pokemon implements Serializable {
    private String name;
    private int pokedexId;
    private HashMap<String,String> moves;
    private int hpMax;
    private int hpBattle;

    public Pokemon(String name,HashMap <String,String>  moves,int pokedexId,int hp) {
        this.name = name;
        this.moves=moves;
        this.pokedexId=pokedexId;
        this.hpMax=hp;
        this.hpBattle=hp;
    }

    public String getName() {
        return name;
    }
    public String getNameUpperCase() {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
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

    public int getPokedexId() {
        return pokedexId;
    }

    public int getHpMax() {
        return hpMax;
    }

    public int getHpBattle() {
        return hpBattle;
    }

    public void setHpBattle(int hpBattle) {
        this.hpBattle = hpBattle;
    }
}
