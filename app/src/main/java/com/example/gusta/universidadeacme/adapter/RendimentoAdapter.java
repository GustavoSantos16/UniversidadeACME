package com.example.gusta.universidadeacme.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gusta.universidadeacme.R;
import com.example.gusta.universidadeacme.model.Rendimento;

import java.util.ArrayList;

/**
 * Created by 17170090 on 13/11/2018.
 */

public class RendimentoAdapter extends ArrayAdapter<Rendimento> {
    RendimentoAdapter adapter;

    public RendimentoAdapter (Context ctx){
        super(ctx, 0, new ArrayList<Rendimento>());
        adapter = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_rendimento, null);

        }

        Rendimento item = getItem(position);

        TextView txt_materia = v.findViewById(R.id.txt_materia);
        TextView txt_nota = v.findViewById(R.id.txt_nota);

        txt_materia.setText(item.getMateria());
        txt_nota.setText(item.getNota());


        return v;
    }
}
