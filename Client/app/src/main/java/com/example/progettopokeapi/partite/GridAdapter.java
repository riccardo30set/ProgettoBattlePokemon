package com.example.progettopokeapi.partite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progettopokeapi.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> arr;
    ArrayList<String> str;

    LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<Integer> arr, ArrayList<String> str) {
        this.context = context;
        this.arr = arr;
        this.str = str;
    }

    @Override
    public int getCount() {
        return arr.size();
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
            view=inflater.inflate(R.layout.stile_alert,null);
        }
        ImageView imageView=view.findViewById(R.id.image_view);
        TextView textView=view.findViewById(R.id.text_view);

        imageView.setImageResource(arr.get(i));
        textView.setText(str.get(i));


        return view;
    }
}
