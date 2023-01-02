package com.example.progettopokeapi.partite;

public class ModelPokemonCheck {
    private String name;
    private int imgId;

    public ModelPokemonCheck(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }


    public int getImgId() {
        return imgId;
    }

}
