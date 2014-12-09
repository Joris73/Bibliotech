package com.joris.bibliotheque.Gestionnaire;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.R;

import java.util.ArrayList;

public class LivreAdapterGestionnaire extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList<Livre> listeLivre;

    public LivreAdapterGestionnaire(Activity a, ArrayList<Livre> listeLivre) {
        activity = a;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listeLivre = listeLivre;
    }

    public int getCount() {
        return listeLivre.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.listview_livre_gestionnaire_item, parent);

        Livre livre = listeLivre.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.list_image_gestion_livre);
        TextView name = (TextView) vi.findViewById(R.id.list_livre_gestion_name);
        TextView auteur = (TextView) vi.findViewById(R.id.list_livre_gestion_auteur);
        TextView annee = (TextView) vi.findViewById(R.id.list_livre_gestion_annee);

        // On affiche le nom du livre
        name.setText(livre.getTitre());
        auteur.setText(livre.getAuteur());
        annee.setText(Integer.toString(livre.getAnnee()));

        vi.setTag(livre);

        return vi;
    }
}