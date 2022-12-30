package com.example.progettopokeapi.partite;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.progettopokeapi.R;
import com.example.progettopokeapi.pokedex.ModelPokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyArrayAdapter extends ArrayAdapter<ModelPartitePokemon> {

    private Context context;
    int selectedCount = 0;
    private LayoutInflater inflater;



    public MyArrayAdapter(Context context, List<ModelPartitePokemon> list) {
        super(context, 0, list);
        inflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ModelPartitePokemon item = getItem(position);


        // Verifica se la vista esistente può essere riutilizzata, altrimenti inflatene una nuova
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.stile_lista_partite, parent, false);
        }

        // Ottieni gli elementi della vista
        ImageView imageView = convertView.findViewById(R.id.imgPokemon);
        TextView textView = convertView.findViewById(R.id.txtNomePokemon);
        final CheckBox checkBox = convertView.findViewById(R.id.idCheck);
        imageView.setImageResource(item.getImg());
        textView.setText(item.getName());
        checkBox.setChecked(item.isCheck());




        checkBox.setTag(item);

        // Imposta un listener per il click sulla checkBox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inverte lo stato di selezione dell'oggetto e aggiorna il contatore di elementi selezionati in base allo stato attuale
                item.setCheck(!item.isCheck());

                // Aggiorna lo stato di selezione della checkBox
                checkBox.setChecked(item.isCheck());

                // Incrementa o decrementa il contatore di elementi selezionati

                if (item.isCheck()) {
                    selectedCount++;
                } else {
                    selectedCount--;
                }


                // Se il contatore raggiunge 6, mostra l'allert con gli elementi selezionati
                if (selectedCount == 6) {
                    showAlertWithSelectedItems(context);
                }
            }
        });

        return convertView;
    }

    @SuppressLint("ResourceType")
    private void showAlertWithSelectedItems(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Imposta il titolo e il messaggio dell'allert
        builder.setTitle("Ecco la tua squadra");
        
        ImageView img=new ImageView(context);
        img.setImageResource(R.navigation.charizard);
        builder.setView(img);
        builder.setMessage("DEVO METTERE LE IMMAGINI E IL TESTO DEI POKEMON");



        // Aggiungi un pulsante "OK" per chiudere l'allert
        builder.setPositiveButton("Invia", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(new Intent(context, PartitaOnlineActivity.class));
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }




}
