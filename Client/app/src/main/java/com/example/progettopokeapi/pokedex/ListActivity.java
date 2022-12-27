package com.example.progettopokeapi.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.progettopokeapi.MainActivity;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    int[] images=new int[898];

    String[] tipoPokemon= new String[898];

    String[] nomePokemon= new String[898];
    ListView listaPokemon;

    LAdapter lAdapter;
    ArrayList<ModelPokemon> arrayList =new ArrayList<ModelPokemon>();
    BottomNavigationView nav;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        String id;


        nav=findViewById(R.id.navbarPok);

        nav.setSelectedItemId(R.id.pokedex);

        nav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pokedex:
                        return true;
                    case R.id.Rooms:
                        Toast.makeText(getApplicationContext(),"Errore inessitente",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });



        for(int i=1; i<899;i++){

            id="pokemon"+i;
            images[i-1]=getResources().getIdentifier(id, "raw", getPackageName());
            if(i<10)
                tipoPokemon[i-1]="#00"+i;
            else if(i<100)
                tipoPokemon[i-1]="#0"+i;
            else
                tipoPokemon[i-1]="#"+i;
            ModelPokemon model=new ModelPokemon(null,tipoPokemon[i-1],images[i-1]);
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

        listaPokemon=(ListView)findViewById(R.id.listPokedex);

        lAdapter=new LAdapter(this,arrayList);

        listaPokemon.setAdapter(lAdapter);


        listaPokemon.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        Intent intent = new Intent(this, PokemonActivity.class);

        listaPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pkName=arrayList.get(i).getName().toString();
                intent.putExtra("pokemonName",pkName);
                String id="";
                for(int t=1; t<(nomePokemon.length+1);t++){
                    if(nomePokemon[t-1].equals(pkName)){
                        id+=t;
                        t=nomePokemon.length;
                    }
                }
                intent.putExtra("pokemonId", id);
                startActivity(intent);
            }
        });


    }
    public void RestCallPokemonNames( ArrayList<ModelPokemon> modelPokemons){
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);
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




    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem.OnActionExpandListener onActionExpandListener=new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                nav.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                nav.setVisibility(View.VISIBLE);
                return true;
            }
        };

        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);


        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Cerca il Pokemon");
        searchView.animate();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    lAdapter.filter("");
                    listaPokemon.clearTextFilter();
                }else{
                    lAdapter.filter(s);
                }
                return false;
            }
        });
        return true;
    }







}