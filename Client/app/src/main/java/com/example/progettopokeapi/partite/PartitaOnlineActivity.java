package com.example.progettopokeapi.partite;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettopokeapi.R;

import java.io.File;
import java.util.ArrayList;

public class PartitaOnlineActivity extends AppCompatActivity {


    RecyclerView.LayoutManager layoutManager;
    PartitaOnlineActivity context;

    ArrayList<Integer> intArrayList;
    ArrayList<String> stringArrayList;


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
                    //Intent intentCombat=

                }
            }
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partite);

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






}