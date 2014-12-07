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
 * A simple {@link Fragment} subclass.
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

        bouton_add_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long isbn = Long.parseLong(edit_isbn.getText().toString());
                String titre = edit_titre.getText().toString();
                String auteur = edit_auteur.getText().toString();
                String editeur = edit_editeur.getText().toString();
                int annee = Integer.parseInt(edit_annee.getText().toString());
                String description = edit_description.getText().toString();

                String SQLrequest = "INSERT INTO livre (ISBN, titre_livre, auteur_livre, " +
                        "editeur_livre, annee_livre, description_livre)" +
                        " VALUES ('" + isbn + "', '" + titre + "', '" + auteur + "', '" + editeur +
                        "', '" + annee + "', '" + description + "' )";

                new RequestTaskAddLivre().execute(SQLrequest);
            }
        });
    }

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

    class RequestTaskAddLivre extends
            AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

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
                for (HashMap<String, String> relivre : response) {

                    MainActivity.listeLivre.add(new Livre(Integer.parseInt(relivre.get("id_res").toString()),
                            Long.parseLong(edit_isbn.getText().toString()), edit_titre.getText().toString(),
                            edit_auteur.getText().toString(), edit_editeur.getText().toString(),
                            Integer.parseInt(edit_annee.getText().toString()), edit_description.getText().toString(), null, null));

                    Toast.makeText(getActivity(), getString(R.string.add_livre), Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new FragmentListeLivresGestionnaire())
                            .commit();
                }
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

    void setFocusChange() {
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
