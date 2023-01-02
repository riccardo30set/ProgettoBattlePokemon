package com.example.progettopokeapi.partite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettopokeapi.R;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemName;
    private final Integer[] imgId;
    int count=0;
    List<ModelPokemonCheck> selectedItems = new ArrayList<>();

    public CustomListAdapter(Activity context, String[] itemName, Integer[] imgId) {
        super(context, R.layout.stile_alert, itemName);
        this.context = context;
        this.itemName = itemName;
        this.imgId = imgId;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.stile_lista, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtNomePokemon);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgPokemon);
        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.idCheck);

        txtTitle.setText(itemName[position]);
        imageView.setImageResource(imgId[position]);
        imageView.setTag(imgId[position]);

        // Aggiungi una checkbox a ciascun elemento della lista e crea una lista di oggetti Item per tenere traccia degli elementi selezionati


        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                if (checkBox.isChecked()) {
                    // Aggiungi l'elemento alla lista degli elementi selezionati
                    selectedItems.add(new ModelPokemonCheck(txtTitle.getText().toString(), (int) imageView.getTag()));
                } else {
                    // Rimuovi l'elemento dalla lista degli elementi selezionati
                    selectedItems.remove(new ModelPokemonCheck(txtTitle.getText().toString(), (int) imageView.getTag()));
                }


                // Mostra l'alert quando l'utente ha selezionato 6 elementi
                if (count == 6) {
                    // Crea l'alert
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Hai selezionato 6 elementi!");


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    // Inflate il layout personalizzato come view dell'alert
                    View alertDialogView = ((Activity) context).getLayoutInflater().inflate(R.layout.alert_layout, null);

                    // Crea l'adapter personalizzato per il RecyclerView
                    ModelAdapter adapter = new ModelAdapter(selectedItems);

                    // Imposta l'adapter personalizzato come adapter del RecyclerView
                    RecyclerView recyclerView = alertDialogView.findViewById(R.id.recycler_view);
                    GridLayoutManager layoutManager=new GridLayoutManager(context,2);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                    // Imposta il layout personalizzato come view dell'alert
                    builder.setView(alertDialogView);

                    // Mostra l'alert
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });

        return rowView;
    }

}
