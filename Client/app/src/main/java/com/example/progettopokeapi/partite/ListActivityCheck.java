package com.example.progettopokeapi.partite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.progettopokeapi.R;
import com.example.progettopokeapi.pokedex.LAdapter;
import com.example.progettopokeapi.pokedex.ModelPokemon;

import java.util.ArrayList;
import java.util.List;

public class ListActivityCheck extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_check);

        String[] itemName = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5","ITEM 6"};
        Integer[] imgId = {R.raw.pokemon100, R.raw.pokemon1, R.raw.pokemon101, R.raw.pokemon102, R.raw.pokemon103,R.raw.pokemon10};
        ListView list=(ListView) findViewById(R.id.listPokedexCheck);

        // Crea un adapter personalizzato per la lista
        CustomListAdapter adapter = new CustomListAdapter(this, itemName, imgId);
        list.setAdapter(adapter);
    }
}