package com.example.progettopokeapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettopokeapi.pokedex.ListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SettingActivity extends AppCompatActivity {


    BottomNavigationView nav;

    //activity volta alla modifica e/o visione dei dati dell'account del client, questi dati verranno
    // utilizzati per giocare in partità, per questo l'applicazione deve verificare la presenza di
    // un nome e un immagine , e nel caso in cui non siano inserite (per qualsiasi motivo)
    // inserire immagine e/o nome di default. Questo accadrà anche al primo accesso all'applicazione.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        nav=findViewById(R.id.navbarPok);
        nav.setSelectedItemId(R.id.settings);
        nav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pokedex:
                        startActivity(new Intent(getApplicationContext(), ListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Rooms:
                        Toast.makeText(getApplicationContext(),"Errore inesitente",Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.settings:
                        return true;
                }

                return false;
            }
        });
        Context context=this;
        //si ricerca il nome utente dell'account nella cartella shared_prefs
        //SharedPreferences
        EditText nameInput=findViewById(R.id.accountNameInput);
        SharedPreferences shared_prefs=context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shared_prefs.edit();
        if(!shared_prefs.contains("nameAccount")){
            editor.putString("nameAccount","bro");
            editor.apply();
        }
        String name=shared_prefs.getString("nameAccount","ThereIsNoNameInSharedPrefs");
        nameInput.setText(name);

        //si ricerca nello storage interno dell'applicazione l'immagine del profilo;
        /*FileInputStream fis=null;
        try { //try-catch che verifica l'esistenza del file inerente all'immagine del profilo
            fis=new FileInputStream("imageAccount.*");
        } catch (FileNotFoundException e) {  //nel caso in cui non ci sia il l'applicazione ne salva uno di default

        }
        ImageView imgAccount=findViewById(R.id.accountImgView);
        Bitmap imageAccount= BitmapFactory.decodeStream(fis);
        imgAccount.setImageBitmap(imageAccount);*/
        //nameInput.setOnFocusChangeListener();
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* editor.putString("nameAccount", String.valueOf(nameInput.getText()));
                editor.apply();
                Toast toast=Toast.makeText(context, "E' stato modificato il nome dell'account", Toast.LENGTH_SHORT);
                toast.show();*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}