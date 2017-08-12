package io.deepsolutions.alarmasmeteo.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<AlarmaItem> ITEMS = new ArrayList<AlarmaItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, AlarmaItem> ITEM_MAP = new HashMap<String, AlarmaItem>();

    private static final int COUNT = 7;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(AlarmaItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id_alarma, item);
    }

    private static AlarmaItem createDummyItem(int position) {
        return new AlarmaItem(String.valueOf(position), "Alarma " + position, true);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Alarm: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class AlarmaItem {
        public final String id_alarma;
        public final String desc_alarma;
        public final boolean estado_ON;

        public AlarmaItem(String id, String desc, boolean estado) {
            this.id_alarma = id;
            this.desc_alarma = desc;
            this.estado_ON = estado;
        }

        @Override
        public String toString() {
            return String.format("%s:%s:%s",this.id_alarma, String.valueOf(this.estado_ON), this.desc_alarma);
        }


    }
}
