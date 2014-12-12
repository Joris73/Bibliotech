package com.joris.bibliotheque.Usager;

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

/**
 * Adaptateur pour afficher les livre dans les listes de l'usager
 */
public class LivreAdapterUsager extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList<Livre> listeLivre;

    public LivreAdapterUsager(Activity a, ArrayList<Livre> listeLivre) {
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
            vi = inflater.inflate(R.layout.listview_livre_usager_item, null);

        Livre livre = listeLivre.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.list_image_usager_livre);
        TextView name = (TextView) vi.findViewById(R.id.list_livre_usager_name);
        TextView auteur = (TextView) vi.findViewById(R.id.list_livre_usager_auteur);
        TextView annee = (TextView) vi.findViewById(R.id.list_livre_usager_annee);

        // On affiche le nom du livre
        name.setText(livre.getTitre());
        auteur.setText(livre.getAuteur());
        annee.setText(Integer.toString(livre.getAnnee()));

        vi.setTag(livre);

        return vi;
    }
}