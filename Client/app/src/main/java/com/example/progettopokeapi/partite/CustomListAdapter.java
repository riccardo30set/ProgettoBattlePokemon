package com.example.progettopokeapi.partite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.progettopokeapi.R;
import com.example.progettopokeapi.pokedex.ModelPokemon;
import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<ModelPokemonCheck> {

    private Activity context;
    List<ModelPokemon> modelPokemonList;
    List<ModelPokemonCheck> selectedItems = new ArrayList<>();
    int count=0;

    public CustomListAdapter(Activity context,List<ModelPokemonCheck> objects) {
        super(context, R.layout.stile_alert, objects);
        this.context = context;
        this.modelPokemonList = modelPokemonList;

    }

    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        final View risultato;

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.stile_lista, null, true);


        if(view==null){
            viewHolder=new ViewHolder(view);


            risultato=view;

            view.setTag(viewHolder);
            view=inflater.inflate(R.layout.stile_lista_pokemon, null);
            viewHolder.imageView=(ImageView) rowView.findViewById(R.id.imgPokemon);
            viewHolder.textView=(TextView)view.findViewById(R.id.txtNomePokemon);
            viewHolder.check=(CheckBox)view.findViewById(R.id.idCheck);

        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.textView.setText(modelPokemonList.get(position).getName());
        viewHolder.imageView.setImageResource(modelPokemonList.get(position).getImg());
        viewHolder.imageView.setTag(modelPokemonList.get(position));

        // Aggiungi una checkbox a ciascun elemento della lista e crea una lista di oggetti Item per tenere traccia degli elementi selezionatiù

        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;

                if (viewHolder.check.isChecked()) {
                    // Aggiungi l'elemento alla lista degli elementi selezionati
                    selectedItems.add(new ModelPokemonCheck(viewHolder.textView.getText().toString(), (int) viewHolder.imageView.getTag()));
                } else {
                    // Rimuovi l'elemento dalla lista degli elementi selezionati
                    selectedItems.remove(new ModelPokemonCheck(viewHolder.textView.getText().toString(), (int) viewHolder.imageView.getTag()));
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imgPokemon);
            textView=itemView.findViewById(R.id.txtNomePokemon);
            check=itemView.findViewById(R.id.idCheck);
        }
    }

}
