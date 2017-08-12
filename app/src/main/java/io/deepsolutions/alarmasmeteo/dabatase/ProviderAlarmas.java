package io.deepsolutions.alarmasmeteo.dabatase;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by Yo on 07/08/2017.
 */

public class ProviderAlarmas extends ContentProvider {
    //Comparador de URIs
    public static final UriMatcher uriMatcher;
    //Casos a comparar
    public static final int ALARMAS_URI = 100;
    public static final int ALARMA_URI_ID = 101;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contrato.AUTORIDAD, "alarmas", ALARMAS_URI);
        uriMatcher.addURI(Contrato.AUTORIDAD, "alarmas/*", ALARMA_URI_ID);
    }
    private BaseDatos bd;
    private ContentResolver resolver;
    private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<Boolean>();

    @Override
    public boolean onCreate(){
        bd = new BaseDatos(getContext());
        resolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public String getType(Uri uri){
        switch (uriMatcher.match(uri)){
            case ALARMAS_URI:
                return Contrato.Alarmas.MIME_COLECCION;
            case ALARMA_URI_ID:
                return Contrato.Alarmas.MIME_RECURSO;
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Obtener base de datos
        SQLiteDatabase db = bd.getWritableDatabase();
        // Comparar Uri
        int match = uriMatcher.match(uri);
        Cursor c;
        switch (match) {
            case ALARMAS_URI:
                //Listado de todos los registros
                c = db.query(BaseDatos.Tablas.ALARMAS, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(resolver, Contrato.Alarmas.URI_CONTENIDO);
                break;
            case ALARMA_URI_ID:
                //Consulta de un solo registro
                String idAlarma = Contrato.Alarmas.obtenerIdAlarma(uri);
                c = db.query(BaseDatos.Tablas.ALARMAS, projection,
                        Contrato.ColumnasAlarma.ID_ALARMA + "=" + "\'" + idAlarma + "\'"
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(resolver, uri);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        //TODO: comprobar el uriMatcher
        SQLiteDatabase db = bd.getWritableDatabase();
        long rowid = db.insertWithOnConflict(BaseDatos.Tablas.ALARMAS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if (rowid > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, rowid);
            if (!isInBatchMode()) {
                // notify all listeners of changes:
                getContext().
                        getContentResolver().
                        notifyChange(itemUri, null);
            }
            return itemUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = bd.getReadableDatabase();
        int delCount = 0;
        switch (uriMatcher.match(uri)){
            case ALARMAS_URI:
                delCount = db.delete(BaseDatos.Tablas.ALARMAS, s, strings);
                break;
            case ALARMA_URI_ID:
                String idr = uri.getLastPathSegment();
                String where = Contrato.ColumnasAlarma.ID_ALARMA + "=" + idr;
                if (!TextUtils.isEmpty(s)) {
                    where += " AND " + s;
                }
                delCount = db.delete(BaseDatos.Tablas.ALARMAS, where, strings);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (delCount > 0 && !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delCount;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = bd.getWritableDatabase();
        int updateCount = 0;
        switch (uriMatcher.match(uri)){
            case ALARMAS_URI:
                updateCount = db.update(BaseDatos.Tablas.ALARMAS, contentValues, s, strings);
                break;
            case ALARMA_URI_ID:
                String idr = uri.getLastPathSegment();
                String where = Contrato.ColumnasAlarma.ID_ALARMA + "=" + idr;
                if (!TextUtils.isEmpty(s)) {
                    where += " AND " + s;
                }
                updateCount = db.update(BaseDatos.Tablas.ALARMAS, contentValues, where, strings);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        if (updateCount > 0 && !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    private boolean isInBatchMode() {
        return mIsInBatchMode.get() != null && mIsInBatchMode.get();
    }




}
