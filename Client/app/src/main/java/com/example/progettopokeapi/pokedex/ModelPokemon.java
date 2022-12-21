package com.example.progettopokeapi.pokedex;

public class ModelPokemon {
    String name;
    String tipo;
    int img;

    public ModelPokemon(String name, String tipo, int img) {
        this.name = name;
        this.tipo = tipo;
        this.img = img;
    }
    public void setName(String name) {
        this.name=name;
    }
    public String getName() {
        return this.name;
    }

    public String getTipo() {
        return this.tipo;
    }

    public int getImg() {
        return this.img;
    }

}
