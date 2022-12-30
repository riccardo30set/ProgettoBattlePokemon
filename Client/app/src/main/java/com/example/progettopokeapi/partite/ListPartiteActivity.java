package com.example.progettopokeapi.partite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.pokedex.ModelPokemon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListPartiteActivity extends AppCompatActivity {

    int[] images=new int[898];

    String[] tipoPokemon= new String[898];

    Boolean[] check=new Boolean[898];

    String[] nomePokemon= new String[898];
    ListView listaPokemon;

    MyArrayAdapter myArrayAdapter;
    ArrayList<ModelPartitePokemon> arrayList =new ArrayList<ModelPartitePokemon>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_partite);
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
            ModelPartitePokemon model=new ModelPartitePokemon(null,tipoPokemon[i-1],images[i-1],false);
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


        // Set the adapter for the ListView
        myArrayAdapter=new MyArrayAdapter(this,arrayList);
        listaPokemon= (ListView) findViewById(R.id.listPokedexPartite);
        listaPokemon.setAdapter(myArrayAdapter);






    }
    public void RestCallPokemonNames( ArrayList<ModelPartitePokemon> modelPokemons){
        RequestQueue queue = Volley.newRequestQueue(ListPartiteActivity.this);
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