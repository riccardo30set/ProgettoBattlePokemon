package com.example.progettopokeapi.partite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progettopokeapi.R;

import java.io.File;
import java.util.ArrayList;

public class GridPlayerAdapter extends BaseAdapter {

    Context context;
    String name;
    Bitmap img;


    LayoutInflater inflater;

    public GridPlayerAdapter(Context context, String name, Bitmap img) {
        this.context = context;
        this.name = name;
        this.img = img;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view==null){
            view=inflater.inflate(R.layout.stile_grid_player,null);
        }
        ImageView imageView=view.findViewById(R.id.imgProfilo);
        TextView textView=view.findViewById(R.id.txtNomeProfilo);

        name="User";
        int drawableResourceId = R.drawable.standard_account_image;
        img=BitmapFactory.decodeResource(context.getResources(),drawableResourceId);

        imageView.setImageBitmap(img);
        textView.setText(name);


        return view;
    }


}
