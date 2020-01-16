package com.example.soapws;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TimbresBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "timbres.bd";
    private static final int VERSION_BD = 1;
    private static final String TABLA_HISTORIAL = "CREATE TABLE HISTORIAL_TIMBRES(USERNAME VARCHAR(50), RFC_ASIGNADOS VARCHAR(13), TIMBRES_ASIGNADOS INTEGER, FECHA TEXT);";
    private static final String TABLA_CLIENTES = "CREATE TABLE CLIENTES(RFC_CLIENTE VARCHAR(13), EMAIL_ADMIN VARCHAR(50));";

    public TimbresBD(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_HISTORIAL);
        db.execSQL(TABLA_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOMBRE_BD);
        db.execSQL(TABLA_HISTORIAL);
    }

    public void agregarRegistro(Historial historial_Reg){
        SQLiteDatabase bd = getWritableDatabase();
        if(bd!=null){
            //bd.execSQL("INSERT INTO HISTORIAL_TIMBRES VALUES('"+historial_Reg.username+"','"+historial_Reg.rfc+"',"+historial_Reg.timbresAsignados+",'"+historial_Reg.date+"'");
            ContentValues values = new ContentValues();
            values.put("USERNAME",historial_Reg.username);
            values.put("RFC_ASIGNADOS", historial_Reg.rfc);
            values.put("TIMBRES_ASIGNADOS", historial_Reg.timbresAsignados);
            values.put("FECHA", historial_Reg.date);
            bd.insert("HISTORIAL_TIMBRES", null, values);
            bd.close();
        }

    }

    public void agregarRfcCliente(String rfc, String email){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteDatabase db_r = getReadableDatabase();
        Cursor cursor = db_r.rawQuery("SELECT * FROM CLIENTES WHERE RFC_CLIENTE ='"+rfc+"';",null);
        if(!cursor.moveToFirst()){
            if(db!=null){
                ContentValues valuesCiente = new ContentValues();
                valuesCiente.put("RFC_CLIENTE",rfc);
                valuesCiente.put("EMAIL_ADMIN",email);
                db.insert("CLIENTES", null, valuesCiente);
                db.close();
            }
        }

    }

    public List<Historial> consultar(){
        SQLiteDatabase db = getReadableDatabase();
        List<Historial> histRes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM HISTORIAL_TIMBRES", null);

        if(cursor.moveToFirst()){
            do{
                Historial hist =  new Historial(cursor.getString(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
                histRes.add(hist);
            }while(cursor.moveToNext());
        }
        return  histRes;

    }

    public List<Cliente> consultaRfc(){
        List<Cliente> clientes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CLIENTES",null);

        if(cursor.moveToFirst()){
            do{
                clientes.add(new Cliente(cursor.getString(0), cursor.getString(1)));
            }while(cursor.moveToNext());
        }
        return  clientes;
    }
}
