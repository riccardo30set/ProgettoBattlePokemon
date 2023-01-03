package com.example.progettopokeapi.partite;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ModelPokemonCheck {
    private String name;
    private int imgId;
    private boolean check;

    public ModelPokemonCheck(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }

    public boolean isCheck() {
        return check;
    }
    public void setCheck(boolean check) {
        this.check = check;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
    public int getImgId() {
        return imgId;
    }

}
