package com.joris.bibliotheque.Gestionnaire;


import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.joris.bibliotheque.R;

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

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


}
