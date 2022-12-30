package com.example.progettopokeapi.partite;

public class ModelPartitePokemon {
    String name;
    String tipo;
    boolean check;
    int img;

    public ModelPartitePokemon(String name, String tipo, int img,boolean check) {
        this.name = name;
        this.tipo=tipo;
        this.img = img;
        this.check=check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
