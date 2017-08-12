package io.deepsolutions.alarmasmeteo.dabatase;

/**
 * Created by Yo on 07/08/2017.
 */

import android.net.Uri;

import java.util.UUID;

/**
 * Contrato con la estructura de la base de datos
 */
public class Contrato {
    interface ColumnasAlarma{
        String ID_ALARMA = "idAlarma;"; //PK
        String DESC = "desc";
        String ESTADO_ON = "estadoON";
    }
    // Autoridad del Content Provider
    public final static String AUTORIDAD = "io.deepsolutions.alarmasmeteo";
    // Uri base
    public final static Uri URI_CONTENIDO_BASE = Uri.parse("content://" + AUTORIDAD);

    public static class Alarmas implements ColumnasAlarma{
        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_ALARMAS).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_ALARMAS;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_ALARMAS;

        /**
         * Construye una {@link Uri} para el {@link #ID_ALQUILER} solicitado.
         */
        public static Uri construirUriAlquiler(String idApartamento) {
            return URI_CONTENIDO.buildUpon().appendPath(idApartamento).build();
        }
        public static String generaIdAlarma(){
            return "A-"+ UUID.randomUUID();
        }

        public static String obtenerIdAlarma(Uri uri) {
            return uri.getLastPathSegment();
        }
    }
    public final static String RECURSO_ALARMAS = "alarmas";
}
