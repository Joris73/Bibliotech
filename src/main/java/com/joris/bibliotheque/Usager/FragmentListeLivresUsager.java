package com.joris.bibliotheque.Usager;

import android.annotation.SuppressLint;
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
 * Fragment qui affiche tous les livres pour l'utilisateur
 */
public class FragmentListeLivresUsager extends Fragment {

    private LivreAdapterUsager adapter;
    private MainActivityUsager context;

    public FragmentListeLivresUsager() {
    }

    @SuppressLint("ValidFragment")
    public FragmentListeLivresUsager(MainActivityUsager context) {
        super();
        this.context = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView list = (ListView) getActivity().findViewById(R.id.list_view_livres_usager);
        adapter = new LivreAdapterUsager(getActivity(), MainActivity.listeLivre);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3) {
                Livre livre = (Livre) v.getTag();
                Intent intent = new Intent(getActivity(), LivreUsagerActivity.class);
                intent.putExtra("idLivre", livre.getIdLivre());
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste_livres_usager, container, false);
    }

    /**
     * Va notifier une modification dans la liste des livre et mettre à jour la liste visuelle
     */
    public void updateList() {
        adapter.notifyDataSetChanged();
    }
}


