package com.joris.bibliotheque.Gestionnaire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Main.MainActivity;
import com.joris.bibliotheque.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Affiche toutes les infos d'un livre d'un point de vu gestionnaire
 */
public class LivreGestionnaireActivity extends Activity {

    private ProgressBar progressbar;
    private Button buttonModifier;
    private Button buttonSupprimer;
    private TextView emprunter;
    private Livre livre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livre_gestionnaire);
        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("idLivre", 0);
            livre = Livre.GetLivreList(MainActivity.listeLivre, id);
            progressbar = (ProgressBar) findViewById(R.id.qui_progress_bar);
            TextView titre = (TextView) findViewById(R.id.tv_titre_gestionnaire);
            TextView annee = (TextView) findViewById(R.id.tv_annee_gestionnaire);
            TextView auteur = (TextView) findViewById(R.id.tv_auteur_gestionnaire);
            TextView editeur = (TextView) findViewById(R.id.tv_editeur_gestionnaire);
            TextView isbn = (TextView) findViewById(R.id.tv_isbn_gestionnaire);
            TextView description = (TextView) findViewById(R.id.tv_description_gestionnaire);
            emprunter = (TextView) findViewById(R.id.tv_emprunter_par_gestionnaire);
            buttonModifier = (Button) findViewById(R.id.bt_modifier);
            buttonSupprimer = (Button) findViewById(R.id.bt_supprimer);

            titre.setText(livre.getTitre());
            annee.setText(Integer.toString(livre.getAnnee()));
            auteur.setText(livre.getAuteur());
            editeur.setText(livre.getEditeur());
            isbn.setText(Long.toString(livre.getISBN()));
            description.setText(livre.getDescription());

            if (livre.isEmprunte()) {
                String SQLrequest = "SELECT * "
                        + "FROM usager U "
                        + "WHERE U.id_usager = " + livre.getIdEmpruntePar();
                new RequestTaskEmprunteur().execute(SQLrequest);

            } else {
                emprunter.setText(getString(R.string.emprunt_personne));
            }

            buttonModifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivityGestionnaire.id_livre = livre.getIdLivre();
                    finish();
                }
            });

            buttonSupprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                    builder.setMessage(getString(R.string.message_dialog))
                            .setCancelable(true)
                            .setPositiveButton(getString(R.string.yes),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(final DialogInterface dialog,
                                                            final int id) {
                                            String SQLrequest = "UPDATE livre SET deleted = " + 1 +
                                                    " WHERE id_livre = " + livre.getIdLivre();

                                            new RequestTaskSupprimer().execute(SQLrequest);
                                        }
                                    })
                            .setNegativeButton(getString(R.string.no), null);
                    builder.create().show();
                }
            });

        }
    }

    /**
     * RequestTask qui va supprimer le livre dans la base, cad passer son argument deleted à 1
     */
    class RequestTaskSupprimer extends
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
                MainActivity.listeLivre.remove(livre);
                Toast.makeText(getApplicationContext(), getString(R.string.supp_livre), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

    /**
     * Permet de récupérer le nom et le prenom de l'usager qui l'a emprunté
     */
    class RequestTaskEmprunteur extends
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
                for (HashMap<String, String> usager : response) {
                    emprunter.setText(" " +
                            usager.get("nom_usager") + " " +
                            usager.get("prenom_usager"));
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }
}
