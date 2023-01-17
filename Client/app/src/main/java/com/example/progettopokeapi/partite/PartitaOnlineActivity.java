package com.example.progettopokeapi.partite;

import static android.content.ContentValues.TAG;
import com.example.progettopokeapi.Client;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettopokeapi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PartitaOnlineActivity extends AppCompatActivity {


    RecyclerView.LayoutManager layoutManager;
    PartitaOnlineActivity context;

    ArrayList<Integer> intArrayList;
    ArrayList<String> stringArrayList;

    Intent intentCombat;

    //It is used to get data from the activity automatically
    ActivityResultLauncher<Intent> activityLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.d(TAG,"onActivityResult");
            if(result.getResultCode()==78){
                Intent inten2=result.getData();

                if(inten2 != null){
                    //I extract the data
                    stringArrayList = inten2.getStringArrayListExtra("nomi");
                    intArrayList = inten2.getIntegerArrayListExtra("immagini");

                    ArrayList<String> nomi=new ArrayList<>();
                    ArrayList<Integer> img =new ArrayList<>();

                    nomi.addAll(stringArrayList);
                    img.addAll(intArrayList);

                    GridAdapter gridAdapter=new GridAdapter(PartitaOnlineActivity.this,img,nomi);

                    GridView grid=findViewById(R.id.gridView);

                    grid.setAdapter(gridAdapter);
                    //preparazione intent contenente dati dei pokemon  per la combat activity
                    for(int i=0;i<nomi.size();i++){
                        String pokemonName= nomi.get(i).toLowerCase();
                        int PokedexId=1;
                        HashMap<String,String> moves=new HashMap<String,String>();
                        int hp=0;
                        String json;
                        try {
                            //lettura del file json
                            InputStream is=getAssets().open("moves.json");
                            int size = is.available();
                            byte[] buffer =new byte[size];
                            is.read(buffer);
                            is.close();
                            json=new String(buffer,"UTF-8");
                            JSONObject general=new JSONObject(json);
                            JSONObject pokemon=general.getJSONObject(pokemonName);
                            // id del pokemon
                            PokedexId=pokemon.getInt("pokedex_number");
                            //HP del pokemon
                            JSONObject stats=pokemon.getJSONObject("base_stats");
                            int hpBase=stats.getInt("hp");
                            hp=(int) (Math.floor(0.01*(2*hpBase+31+Math.floor(0.25*85))*100)+110);
                            JSONArray arrayMoves=pokemon.getJSONArray("moves");
                            //nomi delle mosse e i relativi tipi
                            for(int t=0;t<arrayMoves.length();t++){
                                String moveName=arrayMoves.getJSONObject(t).getString("move_name");
                                String moveType=arrayMoves.getJSONObject(t).getString("type");
                                moves.put(moveName,moveType);
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        //adding data for combat activity (moves,Pokemon name,stats)
                        intentCombat.putExtra("pokemon"+(i+1),new Pokemon(pokemonName,moves,PokedexId,hp));
                    }
                }
            }
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partite);
        intentCombat=new Intent(this,Combat_Activity.class);
        Context context=this;
        SharedPreferences shared_prefs;
        String nome;
        Bitmap img;
        int drawableResourceId = R.drawable.standard_account_image;
        shared_prefs=context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        nome=shared_prefs.getString("nameAccount","ThereIsNoNameInSharedPrefs");
        img=BitmapFactory.decodeResource(getResources(),drawableResourceId);
        String nomeFile=shared_prefs.getString("nameImage","ThereIsNoProfileImage");
        File immagineProfiloDaVisualizzare=cercaFileNelloStorageInterno(nomeFile);
        Bitmap bitmap = BitmapFactory.decodeFile(immagineProfiloDaVisualizzare.getAbsolutePath());

        System.out.println(nome);

        ImageView imgProf=findViewById(R.id.imgProfilo);
        TextView txtProf=findViewById(R.id.txtProfileName);
        TextView gameName = (TextView)findViewById(R.id.txtNomePartita
        );
        gameName.setText(getIntent().getExtras().getString("gameName"));

        imgProf.setImageBitmap(bitmap);
        txtProf.setText(nome);


    }






    public File cercaFileNelloStorageInterno(String nomeFileImmagineProfilo){
        File dir=getFilesDir();
        File[] files = dir.listFiles(); // Get the list of files in the directory
        File immagineProfiloDaVisualizzare=null;
        for (File file : files) {
            Log.d("file name",file.getName());
            if (file.getName().equals(nomeFileImmagineProfilo)) {
                // File was found
                return file;
            }
        }
        return null;
    }


    public void onClickList(View view){
        Intent intent = new Intent(this, ListActivityCheck.class);
        activityLauncher.launch(intent);
    }

    public void onClickBattle(View view){
        AppCompatButton button = (AppCompatButton)findViewById(R.id.battleButton);
        button.setEnabled(false);
        button.setBackgroundColor(Color.GRAY);
        Client.setReady(true);
        Toast.makeText(this,"Sei pronto, in attesa dell'altro giocatore",Toast.LENGTH_LONG).show();
        //startActivity(intentCombat);
    }




}