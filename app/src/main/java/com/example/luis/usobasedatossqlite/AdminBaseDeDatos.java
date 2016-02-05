package com.example.luis.usobasedatossqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
/**
 * Created by luis on 04/02/2016.
 */
public class AdminBaseDeDatos extends SQLiteOpenHelper {

    public AdminBaseDeDatos(Context contexto,String nombre,CursorFactory factory,int version){
        super(contexto,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(id integer primary key,nombre varchar(30),apellido varchar(30))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("create table usuario(id integer primary key,nombre varchar(30),apellido varchar(30))");
    }
}
