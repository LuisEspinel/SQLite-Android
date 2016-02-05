package com.example.luis.usobasedatossqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by luis on 04/02/2016.
 */
public class ControladorBaseDeDatos {
    private Context contexto;

    private AdminBaseDeDatos administradorBD; // <- para crear la base de datos.
    private SQLiteDatabase database;          // <- para obtener la bd.
    private String consulta,id,nombre,apellido;

    public ControladorBaseDeDatos(Context contexto){
        this.contexto=contexto;
        this.consulta=new String("");
        this.id=new String("");
        this.administradorBD=new AdminBaseDeDatos(contexto,"administracion",null,1);
    }

    public void addUser(HashMap datos){
        try{
            this.database=this.administradorBD.getWritableDatabase();  // <- crea o abre la base de datos para lectura y escritura.
            this.id=(String) datos.get("id");
            this.nombre=(String) datos.get("nombre");
            this.apellido=(String) datos.get("apellido");
            ContentValues contentValues=new ContentValues();
            contentValues.put("id",this.id);
            contentValues.put("nombre",this.nombre);
            contentValues.put("apellido", this.apellido);
            this.database.insert("usuario", null, contentValues);
        }catch(SQLiteException e){ Toast.makeText(this.contexto,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show(); }
        finally {
            this.database.close();
        }
    }

    public HashMap getUser(int codigo){
        HashMap result=new HashMap();
        try{
            this.database=this.administradorBD.getWritableDatabase();  // <- crea o abre la base de datos para lectura y escritura.
            Cursor resultadoConsulta;    // <- para manejar los resultados de las consultas.
            // creamos la consulta SQL.
            this.consulta="select nombre,apellido from usuario where id="+codigo;
            //realizar la consulta.
            resultadoConsulta=this.database.rawQuery(this.consulta,null);
            // Mover el cursor al primer resultado de la consulta (Primera fila) si los hay
            // y obtener los datos de esa fila... si no, retorna null.
            if(resultadoConsulta.moveToFirst()){
                result.put("nombre",resultadoConsulta.getString(0));
                result.put("apellido",resultadoConsulta.getString(1));
            }else return null;
        }catch(SQLiteException e){ Toast.makeText(this.contexto,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show(); }
        finally{ this.database.close(); }
        return result;
    }

    public void deleteUser(int codigo){
        try{
            this.database=this.administradorBD.getWritableDatabase();
            int numeroRegistrosBorrados=this.database.delete("usuario","id="+codigo,null);
            if(numeroRegistrosBorrados == 1)
                Toast.makeText(this.contexto,"USUARIO ELIMINADO",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.contexto,"NO SE ENCONTRO EL USUARIO",Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){ Toast.makeText(this.contexto,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show(); }
        finally{ this.database.close(); }
    }

    public void updateUser(HashMap datos){
        try{
            this.database=this.administradorBD.getWritableDatabase();
            this.id=(String) datos.get("id");
            this.nombre=(String) datos.get("nombre");
            this.apellido=(String) datos.get("apellido");
            ContentValues contentValues=new ContentValues();
            contentValues.put("id",this.id);
            contentValues.put("nombre",this.nombre);
            contentValues.put("apellido", this.apellido);
            int numeroRegistrosModificados =this.database.update("usuario", contentValues, "id=" + this.id, null);
            if(numeroRegistrosModificados == 1)
                Toast.makeText(this.contexto,"USUARIO ACTUALIZADO",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this.contexto,"NO SE ACTULIZÓ NADA",Toast.LENGTH_SHORT).show();
        }catch(SQLiteException e){ Toast.makeText(this.contexto,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show(); }
        finally{ this.database.close(); }
    }
}
