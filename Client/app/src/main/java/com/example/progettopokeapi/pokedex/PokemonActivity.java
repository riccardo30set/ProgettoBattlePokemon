



package com.example.progettopokeapi.pokedex;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
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

import java.util.HashMap;

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
        TextView typeText1=findViewById(R.id.typeText);
        TextView typeText2=findViewById(R.id.typeText2);
        TextView typeTextSingle=findViewById(R.id.typeTextSingle);
        TextView descriptionText=findViewById(R.id.descriptionText);
        TextView nomePokemon=findViewById(R.id.txtPokemonName);

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
        nomePokemon.setText(pokemonName);

        HashMap<String, String> colorsType = new HashMap<String, String>();
        colorsType.put("normal","#90A8A77A");
        colorsType.put("fire","#90EE8130");
        colorsType.put("water","#906390F0");
        colorsType.put("electric","#90F7D02C");
        colorsType.put("grass","#907AC74C");
        colorsType.put("ice","#9096D9D6");
        colorsType.put("fighting","#90C22E28");
        colorsType.put("poison","#90A33EA1");
        colorsType.put("ground","#90E2BF65");
        colorsType.put("flying","#90A98FF3");
        colorsType.put("bug","#90A6B91A");
        colorsType.put("rock","#90B6A136");
        colorsType.put("ghost","#90735797");
        colorsType.put("dragon","#906F35FC");
        colorsType.put("dark","#90705746");
        colorsType.put("steel","#90B7B7CE");
        colorsType.put("fairy","#90D685AD");

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

                                    hpText.setText(hpText.getText().toString()+arrayStats[indexHp]+"/255");
                                    hpProgressBar.setProgress(arrayStats[indexHp]);
                                    ObjectAnimator hpAnimation = ObjectAnimator.ofInt(hpProgressBar, "progress", 0, arrayStats[indexHp]);
                                    hpAnimation.setDuration(1500);
                                    hpAnimation.setInterpolator(new LinearInterpolator());
                                    hpAnimation.start();

                                    attackText.setText(attackText.getText().toString()+arrayStats[indexAttack]+"/255");
                                    attackProgressBar.setProgress(arrayStats[indexAttack]);
                                    ObjectAnimator AttackAnimation = ObjectAnimator.ofInt(attackProgressBar, "progress", 0, arrayStats[indexAttack]);
                                    AttackAnimation.setDuration(1500);
                                   AttackAnimation.setInterpolator(new LinearInterpolator());
                                   AttackAnimation.start();

                                    defenseText.setText(defenseText.getText().toString()+arrayStats[indexDefense]+"/255");
                                    defenseProgressBar.setProgress(arrayStats[indexDefense]);
                                    ObjectAnimator defenseAnimation = ObjectAnimator.ofInt(defenseProgressBar, "progress", 0, arrayStats[indexDefense]);
                                    defenseAnimation.setDuration(1500);
                                    defenseAnimation.setInterpolator(new LinearInterpolator());
                                    defenseAnimation.start();

                                    specialAttackText.setText(specialAttackText.getText().toString()+arrayStats[indexSpecialAttack]+"/255");
                                    specialAttackProgressBar.setProgress(arrayStats[indexSpecialAttack]);
                                    ObjectAnimator specialAttackAnimation = ObjectAnimator.ofInt(specialAttackProgressBar, "progress", 0, arrayStats[indexSpecialAttack]);
                                    specialAttackAnimation.setDuration(1500);
                                    specialAttackAnimation.setInterpolator(new LinearInterpolator());
                                    specialAttackAnimation.start();

                                    specialDefenseText.setText(specialDefenseText.getText().toString()+arrayStats[indexSpecialDefense]+"/255");
                                    specialDefenseProgressBar.setProgress(arrayStats[indexSpecialDefense]);
                                    ObjectAnimator specialDefenseAnimation = ObjectAnimator.ofInt(specialDefenseProgressBar, "progress", 0, arrayStats[indexSpecialDefense]);
                                    specialDefenseAnimation.setDuration(1500);
                                    specialDefenseAnimation.setInterpolator(new LinearInterpolator());
                                    specialDefenseAnimation.start();

                                    speedText.setText(speedText.getText().toString()+arrayStats[indexSpeed]+"/255");
                                    speedProgressBar.setProgress(arrayStats[indexSpeed]);
                                    ObjectAnimator speedAnimation = ObjectAnimator.ofInt(speedProgressBar, "progress", 0, arrayStats[indexSpeed]);
                                    speedAnimation.setDuration(1500);
                                    speedAnimation.setInterpolator(new LinearInterpolator());
                                    speedAnimation.start();


                                    JSONArray arrayTypes=response.getJSONArray("types");


                                    float radius = 40.0f;
                                    ShapeDrawable colorpokemon  = new ShapeDrawable(new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null));
                                    ShapeDrawable colortype1  = new ShapeDrawable(new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null));
                                    ShapeDrawable colortype2 = new ShapeDrawable(new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null));
                                    String[] types=new String[2];

                                    for (int i=0;i<arrayTypes.length();i++) {
                                        String type = arrayTypes.getJSONObject(i).getJSONObject("type").getString("name");
                                        types[i] = type;
                                    }
                                    //colore di sfondo del pokemon e dei suoi tipi
                                    colorpokemon.getPaint().setColor(Color.parseColor(colorsType.get(types[0])));
                                    colortype1.getPaint().setColor(Color.parseColor(colorsType.get(types[0])));
                                    pokemonImageView.setBackground(colorpokemon);
                                    typeText1.setBackground(colortype1);
                                    typeText1.setText(types[0]);
                                    if(arrayTypes.length()==2){
                                        colortype2.getPaint().setColor(Color.parseColor(colorsType.get(types[1])));
                                        typeText2.setBackground(colortype2);
                                        typeText2.setText(types[1]);
                                        typeTextSingle.setVisibility(View.INVISIBLE);
                                    }else{
                                        typeText2.setVisibility(View.INVISIBLE);
                                        typeText1.setVisibility(View.INVISIBLE);
                                        typeTextSingle.setText(types[0]);
                                        ShapeDrawable colorSingle  = new ShapeDrawable(new RoundRectShape(new float[]{radius, radius, radius, radius, radius, radius, radius, radius}, null, null));
                                        colorSingle.getPaint().setColor(Color.parseColor(colorsType.get(types[0])));
                                        typeTextSingle.setBackground(colorSingle);

                                    }




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