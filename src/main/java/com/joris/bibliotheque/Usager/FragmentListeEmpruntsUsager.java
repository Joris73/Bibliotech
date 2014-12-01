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


public class FragmentListeEmpruntsUsager extends Fragment {

    private LivreAdapterUsager adapter;
    private MainActivityUsager context;

    public FragmentListeEmpruntsUsager() {
    }

    @SuppressLint("ValidFragment")
    public FragmentListeEmpruntsUsager(MainActivityUsager context) {
        super();
        this.context = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView list = (ListView) getActivity().findViewById(R.id.list_view_liste_emprunts);
        adapter = new LivreAdapterUsager(getActivity(), MainActivity.userCourant.getListeEmprunt());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3) {
                Livre livre = (Livre) v.getTag();
                Intent intent = new Intent(getActivity(), LivreUsagerActivity.class);
                intent.putExtra("idLivre", livre.getIdOuvrage());
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste_emprunts_usager, container, false);
    }

    public void updateList() {
        adapter.notifyDataSetChanged();
    }
}


