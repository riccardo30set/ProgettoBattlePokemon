package com.example.progettopokeapi.partite;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettopokeapi.R;

import java.util.ArrayList;
import java.util.List;

public class PartitaOnlineActivity extends AppCompatActivity {


    RecyclerView.LayoutManager layoutManager;
    PartitaOnlineActivity context;

    ArrayList<Integer> intArrayList;
    ArrayList<String> stringArrayList;



    ActivityResultLauncher<Intent> activityLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.d(TAG,"onActivityResult");

            if(result.getResultCode()==78){
                Intent dioca=result.getData();



                if(dioca != null){
                    stringArrayList = dioca.getStringArrayListExtra("nomi");
                    intArrayList = dioca.getIntegerArrayListExtra("immagini");

                    ArrayList<String> nomi=new ArrayList<>();
                    ArrayList<Integer> img =new ArrayList<>();

                    nomi.addAll(stringArrayList);
                    img.addAll(intArrayList);



                    GridAdapter gridAdapter=new GridAdapter(PartitaOnlineActivity.this,img,nomi);

                    GridView grid=findViewById(R.id.gridView);

                    grid.setAdapter(gridAdapter);






                }
            }
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partita_online);

    }

    public void onClickList(View view){
        Intent intent = new Intent(this, ListActivityCheck.class);
        activityLauncher.launch(intent);
    }






}