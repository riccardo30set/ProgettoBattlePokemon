package com.example.progettopokeapi.partite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettopokeapi.R;
import com.example.progettopokeapi.partite.ListPartiteActivity;
import com.example.progettopokeapi.partite.RecyclerViewAdapter;
import com.example.progettopokeapi.pokedex.ListActivity;

public class PartitaOnlineActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    int []arr={R.raw.pokemon1,R.raw.pokemon2,R.raw.pokemon3,R.raw.pokemon4,R.raw.pokemon5,R.raw.pokemon6};
    String []str={"Bulbasaur","Pokemon2","Pokemon3","Charmander","Charmeleon","Charizard"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partita_online);
        recyclerView=findViewById(R.id.listView);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter=new RecyclerViewAdapter(arr,str);

        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setHasFixedSize(true);
    }

    public void onClickList(View view){
        Intent intent = new Intent(this, ListPartiteActivity.class);
        startActivity(intent);
    }


}