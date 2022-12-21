



package com.example.progettopokeapi.pokedex;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.progettopokeapi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokemonActivity extends AppCompatActivity {

    String pokemonId;
    Resources res;
    int frontResID;
    int currentImageViewID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        TextView heightTextView=findViewById(R.id.heightTextView);
        TextView weightTextView=findViewById(R.id.weightTextView);
        ImageView pokemonImageView=(ImageView) findViewById(R.id.pokemonImageView);
        ProgressBar hpProgressBar=findViewById(R.id.hpProgressBar);
        ProgressBar attackProgressBar=findViewById(R.id.attackProgressBar);
        ProgressBar defenseProgressBar=findViewById(R.id.defenseProgressBar);
        ProgressBar specialAttackProgressBar=findViewById(R.id.specialAttackprogressBar);
        ProgressBar specialDefenseProgressBar=findViewById(R.id.specialDefenseprogressBar);
        ProgressBar speedProgressBar=findViewById(R.id.speedProgressBar);
        TextView descriptionText=findViewById(R.id.descriptionText);
        TextView typeText=findViewById(R.id.typeText);
        TextView hpText=findViewById(R.id.hpText);
        TextView attackText=findViewById(R.id.attackText);
        TextView specialAttackText=findViewById(R.id.specialAttackText);
        TextView defenseText=findViewById(R.id.defenseText);
        TextView specialDefenseText=findViewById(R.id.specialDefenseText);
        TextView speedText=findViewById(R.id.speedText);


        RequestQueue queue = Volley.newRequestQueue(PokemonActivity.this);
        Intent intent = getIntent();
        String pokemonId=intent.getStringExtra("pokemonId");

        String url = "https://pokeapi.co/api/v2/pokemon/"+pokemonId;

        res = getResources();
        String imageFrontName = "pokemon"+pokemonId;
        frontResID = res.getIdentifier(imageFrontName , "raw", getPackageName());
        pokemonImageView.setImageResource(frontResID);
        currentImageViewID=frontResID;
        String pokemonName=intent.getStringExtra("pokemonName");

        setTitle(pokemonName);

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest
                (Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Float height=Float.parseFloat(response.getString("height"))/10;
                                    heightTextView.setText(height.toString()+"m");
                                    Float weight=Float.parseFloat(response.getString("weight"))/10;
                                    weightTextView.setText(weight.toString()+"kg");

                                    int[] arrayStats=new int[6];
                                    int indexHp=0,indexAttack=1,indexDefense=2,indexSpecialAttack=3,indexSpecialDefense=4,indexSpeed=5;

                                    for (int i=0;i<arrayStats.length;i++){
                                        arrayStats[i]=response.getJSONArray("stats").getJSONObject(i).getInt("base_stat");
                                    }

                                    hpText.setText(hpText.getText().toString()+arrayStats[indexHp]);
                                    hpProgressBar.setProgress(arrayStats[indexHp]);
                                    attackText.setText(attackText.getText().toString()+arrayStats[indexAttack]);
                                    attackProgressBar.setProgress(arrayStats[indexAttack]);
                                    defenseText.setText(defenseText.getText().toString()+arrayStats[indexDefense]);
                                    defenseProgressBar.setProgress(arrayStats[indexDefense]);
                                    specialAttackText.setText(specialAttackText.getText().toString()+arrayStats[indexSpecialAttack]);
                                    specialAttackProgressBar.setProgress(arrayStats[indexSpecialAttack]);
                                    specialDefenseProgressBar.setProgress(arrayStats[indexSpecialDefense]);
                                    specialDefenseText.setText(specialDefenseText.getText().toString()+arrayStats[indexSpecialDefense]);
                                    speedText.setText(speedText.getText().toString()+arrayStats[indexSpeed]);
                                    speedProgressBar.setProgress(arrayStats[indexSpeed]);

                                    JSONArray arrayTypes=response.getJSONArray("types");
                                    String types="";
                                    for (int i=0;i<arrayTypes.length();i++){

                                        String type=arrayTypes.getJSONObject(i).getJSONObject("type").getString("name");
                                        types+=type+",";

                                    }
                                    types=types.substring(0,types.length()-1);
                                    typeText.setText(types);

                                    //scherzo fadda ti amo uwuwuwuwu

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        setTitle("error");
                    }

                });
        queue.add(jsonObjectRequest);
        String urlDescription="https://pokeapi.co/api/v2/pokemon-species/"+pokemonId;
        JsonObjectRequest jsonObjectRequestDescription= new JsonObjectRequest
                (Request.Method.GET, urlDescription,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //['flavor_text_entries'][variabile]['version']['name'] versione gioco "sword"
                                    //['flavor_text_entries'][variabile]['language']['name'] lingua "en"



                                    boolean correctDescription=false;
                                    int i=0;
                                    String secondDescription="";
                                   while(!correctDescription && i<response.getJSONArray("flavor_text_entries").length() ){
                                       String value= String.valueOf(i);
                                       Log.d("giro", value);
                                       String gen=response.getJSONArray("flavor_text_entries").getJSONObject(i).getJSONObject("version").getString("name");
                                        String lang=response.getJSONArray("flavor_text_entries").getJSONObject(i).getJSONObject("language").getString("name");
                                        Log.d("lang",lang);
                                        Log.d("gen",gen);
                                        if (lang.equals("en")){
                                            secondDescription=response.getJSONArray("flavor_text_entries").getJSONObject(i).getString("flavor_text");
                                            if ((gen.equals("shield") || gen.equals("sword")) ){
                                                correctDescription=true;
                                                descriptionText.setText(response.getJSONArray("flavor_text_entries").getJSONObject(i).getString("flavor_text"));
                                            }
                                        }
                                        i++;
                                    }
                                   if (descriptionText.getText().equals("...")){
                                       descriptionText.setText(secondDescription);
                                   }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    descriptionText.setText("error");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        descriptionText.setText("error");
                    }

                });
        queue.add(jsonObjectRequestDescription);




        switch (typeText) {
            case "normal":
                pokemonImageView.setBackgroundColor(Color.parseColor("#ff0000"));
                break;
            case "fire":

                break;
            case "water":

                break;
            case "grass":

                break;
            case "electric":

                break;
            case "psychic":

                break;
            case "bug":

                break;
            case "rock":

                break;
            case "ghost":

                break;
            case "dragon":

                break;
            case "dark":

                break;
            case "steel":

                break;
            default:
                break; // Invalid type
        }
        pokemonImageView.setBackgroundColor(Color.parseColor("#ff0000"));


    }


    private int getTypeIndex(String type) {
        switch (type) {
            case "normal": return 0;
            case "fire": return 1;
            case "water": return 2;
            case "grass": return 3;
            case "electric": return 4;
            case "psychic": return 5;
            case "bug": return 6;
            case "rock": return 7;
            case "ghost": return 8;
            case "dragon": return 9;
            case "dark": return 10;
            case "steel": return 11;
            default: return -1; // Invalid type
        }
    }

    public void onClickImage(View view){
        ImageView imgPokemon=(ImageView) view;


        String pokemonId=getIntent().getStringExtra("pokemonId");
        String imageBackName = "pokemonretro"+pokemonId;
        int backResID = res.getIdentifier(imageBackName , "raw", getPackageName());
        if(backResID!=0){
            if (currentImageViewID==frontResID){
                imgPokemon.setImageResource(backResID);
                currentImageViewID=backResID;

            }else if(currentImageViewID==backResID){
                imgPokemon.setImageResource(frontResID);
                currentImageViewID=frontResID;
            }
        }


    }

}