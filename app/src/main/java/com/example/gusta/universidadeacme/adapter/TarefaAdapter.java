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
import com.example.gusta.universidadeacme.model.Tarefa;

import java.util.ArrayList;

public class TarefaAdapter extends ArrayAdapter<Tarefa>{

    public TarefaAdapter(Context ctx, ArrayList<Tarefa> lstTarefa){
        super(ctx, 0 ,lstTarefa);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_tarefa,null);
        }

        Tarefa tarefa = getItem(position);

        TextView txt_titulo_tarefa = v.findViewById(R.id.txt_titulo_tarefa);
        TextView txt_data_tarefa = v.findViewById(R.id.txt_data_tarefa);


        txt_titulo_tarefa.setText(tarefa.getTitulo());
        txt_data_tarefa.setText(tarefa.getData());

        return v;
    }
}
