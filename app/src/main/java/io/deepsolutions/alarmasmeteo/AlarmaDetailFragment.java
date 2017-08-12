package io.deepsolutions.alarmasmeteo;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import io.deepsolutions.alarmasmeteo.dummy.DummyContent;

/**
 * A fragment representing a single Alarma detail screen.
 * This fragment is either contained in a {@link AlarmaListActivity}
 * in two-pane mode (on tablets) or a {@link AlarmaDetailActivity}
 * on handsets.
 */
public class AlarmaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     * TODO: ya no es tan dummy. Renombrar el paquete y la clase.
     */
    private DummyContent.AlarmaItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.id_alarma);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.alarma_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.detail_id_alarma)).setText(mItem.id_alarma);
            ((TextView) rootView.findViewById(R.id.detail_desc)).setText(mItem.desc_alarma);
            ((Switch) rootView.findViewById(R.id.detail_estado_alarma)).setChecked(mItem.estado_ON);
        }

        return rootView;
    }
}
