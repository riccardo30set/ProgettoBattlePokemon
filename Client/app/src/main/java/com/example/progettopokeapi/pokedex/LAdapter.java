package com.example.progettopokeapi.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progettopokeapi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<ModelPokemon> modelPokemonList;
    ArrayList<ModelPokemon> arrayList;
    public LAdapter(Context mContext, List<ModelPokemon> modelPokemonList) {
        context = mContext;
        this.modelPokemonList = modelPokemonList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(modelPokemonList);
    }
    private static class ViewHolder {

        TextView  txtNomePokemon;
        TextView txtTipoPokemon;
        ImageView imgPokemon;

    }
    @Override
    public int getCount() {
        return modelPokemonList.size();
    }

    @Override
    public Object getItem(int i) {
        return modelPokemonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        final View risultato;

        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.stile_lista_pokemon, null);
            viewHolder.txtNomePokemon=(TextView)view.findViewById(R.id.txtNomePokemon);
            viewHolder.txtTipoPokemon=(TextView)view.findViewById(R.id.txtTipoPokemon);
            viewHolder.imgPokemon=(ImageView)view.findViewById(R.id.imgPokemon);

            risultato=view;

            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.txtNomePokemon.setText(modelPokemonList.get(pos).getName());
        viewHolder.txtTipoPokemon.setText(modelPokemonList.get(pos).getTipo());
        viewHolder.imgPokemon.setImageResource(modelPokemonList.get(pos).getImg());

        return view;
    }
    public void filter(String chatTxt){
        chatTxt=chatTxt.toLowerCase(Locale.getDefault());
        modelPokemonList.clear();
        if(chatTxt.length()==0){
            modelPokemonList.addAll(arrayList);
        }else{
            for(ModelPokemon model : arrayList){
                if(model.getName().toLowerCase(Locale.getDefault()).contains(chatTxt)){
                    modelPokemonList.add(model);
                }
            }
        }
        notifyDataSetChanged();

    }
}
