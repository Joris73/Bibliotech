package com.joris.bibliotheque.Gestionnaire;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Main.MainActivity;
import com.joris.bibliotheque.R;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAjoutModifLivreGestionnaire extends Fragment {


    private EditText titre;
    private EditText isbn;
    private EditText auteur;
    private EditText editeur;
    private EditText annee;
    private Button bouton;

    public FragmentAjoutModifLivreGestionnaire() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setFocusChange();

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new mongodbInsert().execute();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ajout_modif_livre_gestionnaire, container, false);
        titre = (EditText) rootview.findViewById(R.id.edit_titre_gest);
        isbn = (EditText) rootview.findViewById(R.id.edit_isbn_gest);
        auteur = (EditText) rootview.findViewById(R.id.edit_auteur_gest);
        editeur = (EditText) rootview.findViewById(R.id.edit_editeur_gest);
        annee = (EditText) rootview.findViewById(R.id.edit_annee_gest);
        bouton = (Button) rootview.findViewById(R.id.bt_ajout_modif);
        return rootview;
    }

    void setFocusChange() {
        titre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(titre.getText().toString())) {
                    titre.setText(getString(R.string.edit_titre));
                } else if (hasFocus && titre.getText().toString().equals(getString(R.string.edit_titre))) {
                    titre.setText("");
                }
            }
        });


        isbn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(isbn.getText().toString())) {
                    isbn.setText(getString(R.string.edit_isbn));
                } else if (hasFocus && isbn.getText().toString().equals(getString(R.string.edit_isbn))) {
                    isbn.setText("");
                }
            }
        });

        auteur.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(auteur.getText().toString())) {
                    auteur.setText(getString(R.string.edit_auteur));
                } else if (hasFocus && auteur.getText().toString().equals(getString(R.string.edit_auteur))) {
                    auteur.setText("");
                }
            }
        });

        editeur.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(editeur.getText().toString())) {
                    editeur.setText(getString(R.string.edit_editeur));
                } else if (hasFocus && editeur.getText().toString().equals(getString(R.string.edit_editeur))) {
                    editeur.setText("");
                }
            }
        });

        annee.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(annee.getText().toString())) {
                    annee.setText(getString(R.string.edit_annee));
                } else if (hasFocus && annee.getText().toString().equals(getString(R.string.edit_annee))) {
                    annee.setText("");
                }
            }
        });
    }

    class mongodbInsert extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MongoClient mongoClient = new MongoClient("192.168.0.15");
                DB db = mongoClient.getDB("test");
                Log.wtf("OUAAAAAAAAAAA", db.getCollectionNames().toString());
                DBCollection coll = db.getCollection("biblio");

                coll.insert(new BasicDBObject().append("ISBN", Integer.parseInt(isbn.getText().toString()))
                        .append("titre", titre.getText().toString()).append("auteur", auteur.getText().toString())
                        .append("editeur", editeur.getText().toString()).append("annee", Integer.parseInt(annee.getText().toString())));

                MainActivity.listeLivre.add(new Livre(String.valueOf(MainActivity.listeLivre.size()),
                        Integer.parseInt(isbn.getText().toString()), titre.getText().toString(), auteur.getText().toString(),
                        editeur.getText().toString(), Integer.parseInt(annee.getText().toString()), null, null));

            } catch (UnknownHostException e) {
                Log.wtf("FUCK", e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            Toast.makeText(getActivity(), getString(R.string.add_livre), Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new FragmentListeLivresGestionnaire())
                    .commit();
        }
    }

}
