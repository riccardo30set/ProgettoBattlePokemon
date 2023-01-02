package com.example.progettopokeapi.partite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.pokedex.LAdapter;
import com.example.progettopokeapi.pokedex.ListActivity;
import com.example.progettopokeapi.pokedex.ModelPokemon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListActivityCheck extends AppCompatActivity {

    int[] images=new int[898];

    String[] tipoPokemon= new String[898];

    String[] nomePokemon= new String[898];

    ListView listaPokemon;

    CustomListAdapter modelAdapter;
    ArrayList<ModelPokemonCheck> arrayList =new ArrayList<ModelPokemonCheck>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_check);
        String id;

        for(int i=1; i<899;i++){

            id="pokemon"+i;
            images[i-1]=getResources().getIdentifier(id, "raw", getPackageName());
            if(i<10)
                tipoPokemon[i-1]="#00"+i;
            else if(i<100)
                tipoPokemon[i-1]="#0"+i;
            else
                tipoPokemon[i-1]="#"+i;
            ModelPokemonCheck model=new ModelPokemonCheck(null,images[i-1]);
            arrayList.add(model);
        }
        arrayList.get(0).setName("Bulbasaur");
        arrayList.get(1).setName("Ivysaur");
        arrayList.get(2).setName("Venusaur");
        arrayList.get(3).setName("Charmander");
        arrayList.get(4).setName("Charmeleon");
        arrayList.get(5).setName("Charizard");
        arrayList.get(6).setName("Squirtle");
        arrayList.get(7).setName("Wartortole");
        arrayList.get(8).setName("Blastoise");
        arrayList.get(9).setName("Caterpie");
        arrayList.get(10).setName("Metapod");
        arrayList.get(11).setName("Butterfly");
        this.RestCallPokemonNames(arrayList);

        listaPokemon=(ListView)findViewById(R.id.listPokedexCheck);


        modelAdapter=new CustomListAdapter(this,arrayList);


        listaPokemon.setAdapter((ListAdapter) modelAdapter);

        String[] itemName = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5","ITEM 6"};
        int[] imgId = {R.raw.pokemon100, R.raw.pokemon1, R.raw.pokemon101, R.raw.pokemon102, R.raw.pokemon103,R.raw.pokemon10};
        ListView list=(ListView) findViewById(R.id.listPokedexCheck);

        // Crea un adapter personalizzato per la lista
        CustomListAdapter adapter = new CustomListAdapter(this,arrayList);
        list.setAdapter(adapter);
    }

    public void RestCallPokemonNames( ArrayList<ModelPokemonCheck> modelPokemons){
        RequestQueue queue = Volley.newRequestQueue(ListActivityCheck.this);
        String url = "https://pokeapi.co/api/v2/pokedex/1/";

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                for(int i=1;i<899;i++){
                                    try {
                                        modelPokemons.get(i-1).setName(response.getJSONArray("pokemon_entries").getJSONObject(i-1).getJSONObject("pokemon_species").getString("name"));
                                        modelPokemons.get(i-1).setName(modelPokemons.get(i-1).getName().substring(0,1).toUpperCase()+modelPokemons.get(i-1).getName().substring(1,modelPokemons.get(i-1).getName().length()));
                                        nomePokemon[i-1]=modelPokemons.get(i-1).getName();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        for(int i=1;i<899;i++){
                            modelPokemons.get(i-1).setName("ERRORE, VIVA NAPOLI");
                        }
                    }
                });
        queue.add(jsonObjectRequest);
    }

}