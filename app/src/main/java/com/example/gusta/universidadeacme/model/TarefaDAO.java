package com.example.gusta.universidadeacme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TarefaDAO {

    private static TarefaDAO instance;

    public static TarefaDAO getInstance(){
        if(instance == null){
            instance = new TarefaDAO();
        }
        return instance;
    }

    public Boolean inserirTarefa(Context context, Tarefa t){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("titulo", t.getTitulo());
        valores.put("descricao", t.getDescricao());
        valores.put("data",t.getData());

        Long id = db.insert("tbl_tarefa",null,valores);

        if(id != -1){
            return true;
        }else{
            return false;
        }

    }
    public Boolean atualizar (Context context, Tarefa t){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("titulo", t.getTitulo());
        valores.put("descricao", t.getDescricao());
        valores.put("data",t.getData());

        db.update("tbl_tarefa",valores,"_idTarefa = ?",new String[]{String.valueOf(t.getIdTarefa())});
        return true;
    }

    public Boolean remover(Context context, Integer id){
        SQLiteDatabase db = new DbHelper(context).getWritableDatabase();
        db.delete("tbl_tarefa", "_idTarefa = ?",
                new String[]{id.toString()});

        return false;
    }


    public Tarefa selecionarUma(Context context, int id){
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();
        String sql = "select * from tbl_tarefa where _idTarefa ="+id;

        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            Tarefa t = new Tarefa();
            t.setIdTarefa(cursor.getInt(0));
            t.setTitulo(cursor.getString(1));
            t.setDescricao(cursor.getString(2));
            t.setData(cursor.getString(3));

            cursor.close();

            return t;

        }
        return null;
    }

    public ArrayList<Tarefa> selecionarTodas(Context context){
        ArrayList<Tarefa> retorno = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "select * from tbl_tarefa";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Tarefa t = new Tarefa();
            t.setIdTarefa(cursor.getInt(0));
            t.setTitulo(cursor.getString(1));
            t.setDescricao(cursor.getString(2));
            t.setData(cursor.getString(3));

            retorno.add(t);
        }
        return retorno;

    }

    public ArrayList<Tarefa> selecionarPorData(Context context, String data){
        ArrayList<Tarefa> retorno = new ArrayList<>();
        SQLiteDatabase db = new DbHelper(context).getReadableDatabase();

        String sql = "select * from tbl_tarefa where data = '"+data+"'";

        Cursor cursor = db.rawQuery(sql, null);

        while(cursor.moveToNext()){
            Tarefa t = new Tarefa();
            t.setIdTarefa(cursor.getInt(0));
            t.setTitulo(cursor.getString(1));
            t.setDescricao(cursor.getString(2));
            t.setData(cursor.getString(3));


            retorno.add(t);
        }
        return retorno;

    }


}
