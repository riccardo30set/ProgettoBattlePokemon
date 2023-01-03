package com.example.progettopokeapi.partite;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.pokedex.ModelPokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomListAdapter extends BaseAdapter {

    private final Activity context;
    int count=0;
    Context c=new ListActivityCheck();
    private List<ModelPokemonCheck> items;
    List<ModelPokemonCheck> selectedItems = new ArrayList<>();
    ArrayList<String> listaNomi=new ArrayList<>();
    ArrayList<Integer> imgPok=new ArrayList<>();





    public CustomListAdapter(Activity context, List<ModelPokemonCheck> items) {
        this.context = context;
        this.items = items;
    }

    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the layout for the row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.stile_lista, parent, false);
        }

        // Get the item for the current position
        ModelPokemonCheck item = items.get(position);

        // Get the views to bind the data to
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtNomePokemon);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgPokemon);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.idCheck);

        // Bind the data to the views
        txtTitle.setText(item.getName());
        imageView.setImageResource(item.getImgId());
        imageView.setTag(item.getImgId());
        checkBox.setChecked(item.isCheck());

        checkBox.setTag(item);


        // Set the click listener for the checkbox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                item.setCheck(!item.isCheck());

                // Aggiorna lo stato di selezione della checkBox
                checkBox.setChecked(item.isCheck());

                // Incrementa o decrementa il contatore di elementi selezionati

                if (item.isCheck()) {
                    count++;
                } else {
                    count--;
                }

                if (checkBox.isChecked()) {
                    // Aggiungi l'elemento alla lista degli elementi selezionati
                    selectedItems.add(new ModelPokemonCheck(txtTitle.getText().toString(), (int) imageView.getTag()));
                    listaNomi.add(txtTitle.getText().toString());
                    imgPok.add((Integer) imageView.getTag());
                } else {
                    // Rimuovi l'elemento dalla lista degli elementi selezionati
                    selectedItems.remove(new ModelPokemonCheck(txtTitle.getText().toString(), (int) imageView.getTag()));
                    listaNomi.add(txtTitle.getText().toString());
                    imgPok.add((Integer) imageView.getTag());

                }


                // Mostra l'alert quando l'utente ha selezionato 6 elementi
                if (count == 6) {
                    // Crea l'alert
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("ECCO LA TUA SQUADRA");


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent();
                            intent.putStringArrayListExtra("nomi",listaNomi);
                            intent.putIntegerArrayListExtra("immagini",imgPok);
                            context.setResult(78,intent);
                            context.finish();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
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

        // Return the completed view to render on screen
        return convertView;
    }




}
