package com.example.gusta.universidadeacme.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "uniacme.db";
    private static int DB_VERSION = 1;

    public DbHelper (Context ctx){super(ctx, DB_NAME, null,DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tbl_tarefa(_idTarefa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "descricao TEXT NOT NULL, " +
                "data DATE );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tbl_tarefa");
        onCreate(db);
    }
}
