package io.deepsolutions.alarmasmeteo.dabatase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import io.deepsolutions.alarmasmeteo.dabatase.Contrato.Alarmas;

/**
 * Created by Yo on 07/08/2017.
 */

public class BaseDatos extends SQLiteOpenHelper {
    static final int VERSION = 1;
    static final String NOMBRE_BD = "alarmas.db";
    interface Tablas {
        String ALARMAS = "alarmas";
    }
    public BaseDatos(Context contexto){
        super(contexto, NOMBRE_BD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sqlCreate= "CREATE TABLE" + Tablas.ALARMAS + "("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Alarmas.ID_ALARMA + " TEXT UNIQUE,"
                + Alarmas.DESC + " TEXT"
                + Alarmas.ESTADO_ON + " INTEGER)";
        db.execSQL(sqlCreate);

        //Cargo unos registros de ejemplo
        //TODO: eliminar los registros de ejemplo
        ContentValues valores = new ContentValues();
        valores.put(Alarmas.ID_ALARMA, Alarmas.generaIdAlarma());
        valores.put(Alarmas.DESC, "primera alarma de test");
        valores.put(Alarmas.ESTADO_ON, 0);
        db.insertOrThrow(Tablas.ALARMAS, null, valores);
        valores.put(Alarmas.ID_ALARMA, Alarmas.generaIdAlarma());
        valores.put(Alarmas.DESC, "segunda alarma de test");
        valores.put(Alarmas.ESTADO_ON, 1);
        db.insertOrThrow(Tablas.ALARMAS, null, valores);
        valores.put(Alarmas.ID_ALARMA, Alarmas.generaIdAlarma());
        valores.put(Alarmas.DESC, "tercera alarma de test");
        valores.put(Alarmas.ESTADO_ON, 0);
        db.insertOrThrow(Tablas.ALARMAS, null, valores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        try{
            db.execSQL("DROP TABLE IF EXISTS " + Tablas.ALARMAS);
        }catch(SQLiteException e){
            //TODO: tratamiento de excepcion
        }
        onCreate(db);
    }

}
