package com.joris.bibliotheque.Gestionnaire;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Main.MainActivity;
import com.joris.bibliotheque.R;

/**
 * Fragment qui affiche la liste des livres d'un point de vu gestionnaire
 */
public class FragmentListeLivresGestionnaire extends Fragment {

    private LivreAdapterGestionnaire adapter;

    public FragmentListeLivresGestionnaire() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView list = (ListView) getActivity().findViewById(R.id.list_view_livres_gestionnaire);
        adapter = new LivreAdapterGestionnaire(getActivity(), MainActivity.listeLivre);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3) {
                Livre livre = (Livre) v.getTag();
                Intent intent = new Intent(getActivity(), LivreGestionnaireActivity.class);
                intent.putExtra("idLivre", livre.getIdLivre());
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste_livres_gestionnaire, container, false);
    }

    /**
     * Va notifier que la liste des livre à changer et mettre à jour la liste
     */
    public void updateList() {
        adapter.notifyDataSetChanged();
    }
}


