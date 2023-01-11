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
}
