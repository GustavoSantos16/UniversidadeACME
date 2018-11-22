package com.example.gusta.universidadeacme.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.model.Evento;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventoAdapter extends ArrayAdapter<Evento>{

   EventoAdapter adapter;

    public EventoAdapter (Context ctx){
        super(ctx, 0, new ArrayList<Evento>());
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, null);

        }

        Evento item = getItem(position);

        ImageView icone = v.findViewById(R.id.img_view);

        String imagem = getContext().getString(R.string.caminho_img_evento_noticia)+item.getFoto();



        Picasso.get().load(imagem).into(icone);


        TextView txt_titulo = v.findViewById(R.id.txt_titulo);
        TextView txt_data = v.findViewById(R.id.txt_item_data);

        txt_data.setText(item.getInicio());
        txt_titulo.setText(item.getNome());

        return v;
    }
}
