package com.joris.bibliotheque.Gestionnaire;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Main.MainActivity;
import com.joris.bibliotheque.R;
import com.joris.bibliotheque.zxingqrcode.IntentIntegrator;
import com.joris.bibliotheque.zxingqrcode.IntentResult;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fragment gérant l'ajout ou la modification d'un livre
 */
public class FragmentAjoutModifLivreGestionnaire extends Fragment {


    private EditText edit_titre;
    private EditText edit_isbn;
    private EditText edit_auteur;
    private EditText edit_editeur;
    private EditText edit_annee;
    private Button bouton_add_mod;
    private EditText edit_description;
    private ProgressBar progressbar;
    private Button bouton_scan;
    private Fragment context;

    private long isbn;
    private int annee;
    private String titre;
    private String auteur;
    private String editeur;
    private String description;
    private Bundle bundle;
    private Livre livre;

    public FragmentAjoutModifLivreGestionnaire() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setFocusChange();
        context = this;

        bouton_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(context);
                integrator.initiateScan();
            }
        });

        bundle = this.getArguments();
        // bundle null = création d'un livre
        if (bundle != null) {
            int id = bundle.getInt("id_livre", 0);
            livre = Livre.GetLivreList(MainActivity.listeLivre, id);
            edit_titre.setText(livre.getTitre());
            edit_isbn.setText(Long.toString(livre.getISBN()));
            edit_auteur.setText(livre.getAuteur());
            edit_editeur.setText(livre.getEditeur());
            edit_annee.setText(Integer.toString(livre.getAnnee()));
            edit_description.setText(livre.getDescription());
            bouton_add_mod.setText(R.string.bt_modifier);
            bouton_add_mod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recupererValeurs()) {
                        String SQLrequest = "UPDATE livre SET ISBN = '" + isbn + "', " +
                                "titre_livre = '" + titre + "', " +
                                "auteur_livre = '" + auteur + "', " +
                                "editeur_livre = '" + editeur + "', " +
                                "annee_livre = '" + annee + "', " +
                                "description_livre = '" + description + "'" +
                                " WHERE id_livre = " + livre.getIdLivre();

                        new RequestTask().execute(SQLrequest);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.probleme_champs), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            bouton_add_mod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recupererValeurs()) {
                        String SQLrequest = "INSERT INTO livre (ISBN, titre_livre, auteur_livre, " +
                                "editeur_livre, annee_livre, description_livre)" +
                                " VALUES ('" + isbn + "', '" + titre + "', '" + auteur + "', '" + editeur +
                                "', '" + annee + "', '" + description + "' )";

                        new RequestTask().execute(SQLrequest);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.probleme_champs), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    /**
     * Résultat du scan de l'isbn
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanResult.getContents() != null) {
                edit_isbn.setText(scanResult.getContents());
                Log.i("SCAN_result", scanResult.getContents());
            } else {
                Log.e("SCAN_result", "Le resultat du scan est null");
            }
        }
    }

    /**
     * RequestTask qui va géré la modification et l'ajout d'un livre toujours en fonction du bundle
     */
    class RequestTask extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                String... SQLrequest) {
            return MainActivity.request.executeRequest(SQLrequest[0]);
        }

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> response) {
            if (response != null) {
                if (bundle != null) {
                    livre.setTitre(titre);
                    livre.setISBN(isbn);
                    livre.setAuteur(auteur);
                    livre.setEditeur(editeur);
                    livre.setDescription(description);
                    livre.setAnnee(annee);
                    Toast.makeText(getActivity(), getString(R.string.modif_livre), Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.listeLivre.add(new Livre(Integer.parseInt(response.get(0).get("id_res")),
                            Long.parseLong(edit_isbn.getText().toString()), edit_titre.getText().toString(),
                            edit_auteur.getText().toString(), edit_editeur.getText().toString(),
                            Integer.parseInt(edit_annee.getText().toString()), edit_description.getText().toString(), null, null));
                    Toast.makeText(getActivity(), getString(R.string.add_livre), Toast.LENGTH_SHORT).show();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new FragmentListeLivresGestionnaire())
                        .commit();
            } else {
                Toast.makeText(getActivity(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_ajout_modif_livre_gestionnaire, container, false);
        progressbar = (ProgressBar) rootview.findViewById(R.id.add_progress_bar);
        edit_titre = (EditText) rootview.findViewById(R.id.edit_titre_gest);
        edit_isbn = (EditText) rootview.findViewById(R.id.edit_isbn_gest);
        edit_auteur = (EditText) rootview.findViewById(R.id.edit_auteur_gest);
        edit_editeur = (EditText) rootview.findViewById(R.id.edit_editeur_gest);
        edit_annee = (EditText) rootview.findViewById(R.id.edit_annee_gest);
        edit_description = (EditText) rootview.findViewById(R.id.edit_description_gest);
        bouton_add_mod = (Button) rootview.findViewById(R.id.bt_ajout_modif);
        bouton_scan = (Button) rootview.findViewById(R.id.bt_scan_isbn);
        return rootview;
    }

    /**
     * @return vrai si champs valide, faux sinon
     */
    private boolean recupererValeurs() {
        try {
            isbn = Long.parseLong(edit_isbn.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), getString(R.string.probleme_isbn), Toast.LENGTH_SHORT).show();
        }
        try {
            annee = Integer.parseInt(edit_annee.getText().toString());
            if (edit_annee.getText().toString().length() != 4)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), getString(R.string.probleme_date), Toast.LENGTH_SHORT).show();
        }
        titre = edit_titre.getText().toString();
        titre = titre.replaceAll("'", "''");
        auteur = edit_auteur.getText().toString();
        auteur = auteur.replaceAll("'", "''");
        editeur = edit_editeur.getText().toString();
        editeur = editeur.replaceAll("'", "''");
        description = edit_description.getText().toString();
        description = description.replaceAll("'", "''");

        if (titre.isEmpty() || auteur.isEmpty() || editeur.isEmpty() || description.isEmpty())
            return false;

        return true;
    }

    /**
     * Va changer le text dans les edittext en fonction du focus
     */
    private void setFocusChange() {
        edit_titre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_titre.getText().toString())) {
                    edit_titre.setText(getString(R.string.edit_titre));
                } else if (hasFocus && edit_titre.getText().toString().equals(getString(R.string.edit_titre))) {
                    edit_titre.setText("");
                }
            }
        });

        edit_isbn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_isbn.getText().toString())) {
                    edit_isbn.setText(getString(R.string.edit_isbn));
                } else if (hasFocus && edit_isbn.getText().toString().equals(getString(R.string.edit_isbn))) {
                    edit_isbn.setText("");
                }
            }
        });

        edit_auteur.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_auteur.getText().toString())) {
                    edit_auteur.setText(getString(R.string.edit_auteur));
                } else if (hasFocus && edit_auteur.getText().toString().equals(getString(R.string.edit_auteur))) {
                    edit_auteur.setText("");
                }
            }
        });

        edit_editeur.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_editeur.getText().toString())) {
                    edit_editeur.setText(getString(R.string.edit_editeur));
                } else if (hasFocus && edit_editeur.getText().toString().equals(getString(R.string.edit_editeur))) {
                    edit_editeur.setText("");
                }
            }
        });

        edit_annee.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_annee.getText().toString())) {
                    edit_annee.setText(getString(R.string.edit_annee));
                } else if (hasFocus && edit_annee.getText().toString().equals(getString(R.string.edit_annee))) {
                    edit_annee.setText("");
                }
            }
        });

        edit_description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_description.getText().toString())) {
                    edit_description.setText(getString(R.string.edit_description));
                } else if (hasFocus && edit_description.getText().toString().equals(getString(R.string.edit_description))) {
                    edit_description.setText("");
                }
            }
        });
    }
}
