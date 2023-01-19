package com.example.progettopokeapi;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettopokeapi.partite.Combat_Activity;
import com.example.progettopokeapi.pokedex.ListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingActivity extends AppCompatActivity {
    Context context=this;
    BottomNavigationView nav;
    ImageView imgAccount;
    SharedPreferences shared_prefs;
    SharedPreferences.Editor editor;
    public static final int PICK_IMAGE = 1;


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
                    case R.id.settings:
                        return true;
                }

                return false;
            }
        });

        imgAccount=findViewById(R.id.accountImgView);
        //si ricerca il nome utente dell'account nella cartella shared_prefs
        //SharedPreferences
        EditText nameInput=findViewById(R.id.accountNameInput);
        shared_prefs=context.getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        editor=shared_prefs.edit();



        if(shared_prefs.getString("nameAccount","").equals("")){
            editor.putString("nameAccount","user");
            editor.apply();
        }
        String name=shared_prefs.getString("nameAccount","ThereIsNoNameInSharedPrefs");
        nameInput.setText(name);

        nameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    editor.putString("nameAccount", String.valueOf(nameInput.getText()));
                    editor.apply();
                    Toast toast=Toast.makeText(context, "E' stato modificato il nome dell'account", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast=Toast.makeText(context, "Puoi modificare il nome", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        imgAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        FrameLayout layout = (FrameLayout) findViewById(R.id.frameLayout);
        layout.setZ(1);
        nameInput.setZ(2);
        imgAccount.setZ(2);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameInput.clearFocus();
            }
        });





        // inserimento dell'immmagine entrati nella schermata
        // si controlla nelle prefences se esiste il nome dell'immagine del profilo
        // se esiste si procede con l'inserimento dell'immagine dallo storage interno
        // se non esiste si inizia con l'inserimento con un immagine standard "standard_account_image.png" successivamente
        // si scrive il nome del file standard nelle prefs

        if(shared_prefs.getString("nameImage","").equals("")){
            editor.putString("nameImage","standard_account_image.png");
            editor.apply();
        }
        // salvare il file dell'immagine del profilo standard dalla drawable alla memoria interna se non esiste
        String nomeFileImmagineProfilo=shared_prefs.getString("nameImage","ThereIsNoProfileImage");
        if(nomeFileImmagineProfilo.equals("standard_account_image.png")){
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.standard_account_image);
            salvaFileNelloStorageInterno(bitmap,"standard_account_image",false);
        }
        Log.d("--fileNameSaved",shared_prefs.getString("nameImage",""));
        File immagineProfiloDaVisualizzare=cercaFileNelloStorageInterno(nomeFileImmagineProfilo);
        Bitmap bitmap = BitmapFactory.decodeFile(immagineProfiloDaVisualizzare.getAbsolutePath());
        imgAccount.setImageBitmap(bitmap);
        imgAccount.setZ(2);

    }

    public void onClickIMG(View v){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();
            ContentResolver resolver = getContentResolver();
            InputStream in = null;
            try {
                in = resolver.openInputStream(fileUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap fileBitmap=BitmapFactory.decodeStream(in);

            // Ottieni il percorso dell'immagine selezionata
            imgAccount.setImageURI(fileUri);
            String fileName=fileUri.getLastPathSegment();
            salvaFileNelloStorageInterno(fileBitmap,fileName,true);
            Log.d("nameImage",shared_prefs.getString("nameImage","ThereIsNoProfileImage"));
        }
    }

    public void salvaFileNelloStorageInterno(Bitmap fileBitmap, String fileName,boolean saveOnPrefs){
        FileOutputStream outputStream= null;
        File directory=context.getFilesDir();
        File fileDaSalvare=new File(directory,fileName+".png");
        try {
            outputStream = new FileOutputStream(fileDaSalvare);
            fileBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            if(saveOnPrefs==true){
                editor.putString("nameImage",fileDaSalvare.getName());
                editor.apply();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}