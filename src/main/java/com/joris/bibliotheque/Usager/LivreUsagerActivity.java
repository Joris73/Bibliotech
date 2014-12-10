package com.joris.bibliotheque.Usager;

import android.app.Activity;
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

public class LivreUsagerActivity extends Activity {

    private ProgressBar progressbar;
    private Button button;
    private Livre livre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livre_usager);
        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("idLivre", 0);
            livre = Livre.GetLivreList(MainActivity.listeLivre, id);
            progressbar = (ProgressBar) findViewById(R.id.qui_progress_bar);
            TextView titre = (TextView) findViewById(R.id.tv_titre_usager);
            TextView annee = (TextView) findViewById(R.id.tv_annee_usager);
            TextView auteur = (TextView) findViewById(R.id.tv_auteur_usager);
            TextView editeur = (TextView) findViewById(R.id.tv_editeur_usager);
            TextView isbn = (TextView) findViewById(R.id.tv_isbn_usager);
            TextView description = (TextView) findViewById(R.id.tv_description_gestionnaire);
            button = (Button) findViewById(R.id.btEmpRend);

            titre.setText(livre.getTitre());
            annee.setText(Integer.toString(livre.getAnnee()));
            auteur.setText(livre.getAuteur());
            editeur.setText(livre.getEditeur());
            isbn.setText(Long.toString(livre.getISBN()));
            description.setText(livre.getDescription());

            if (livre.isEmprunte()) {
                if (livre.getIdEmpruntePar() == MainActivity.userCourant.getIdUsager()) {
                    button.setText(getString(R.string.bt_rendre));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String SQLrequest = "DELETE FROM emprunt WHERE id_usager_emprunt = " +
                                    MainActivity.userCourant.getIdUsager() + " and id_livre_emprunt = " +
                                    livre.getIdLivre();

                            new RequestTaskRendre().execute(SQLrequest);
                        }
                    });
                } else {
                    button.setVisibility(View.INVISIBLE);
                }
            } else {
                button.setText(getString(R.string.bt_emprunt));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String SQLrequest = "INSERT INTO emprunt (id_usager_emprunt, id_livre_emprunt)" +
                                " VALUES ('" + MainActivity.userCourant.getIdUsager() + "', '" + livre.getIdLivre() + "' )";

                        new RequestTaskAddEmprunt().execute(SQLrequest);
                    }
                });
            }
        }
    }

    class RequestTaskAddEmprunt extends
            AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                String... SQLrequest) {
            String request = "UPDATE livre SET id_emprunteur = " + MainActivity.userCourant.getIdUsager() +
                    " WHERE id_livre = " + livre.getIdLivre();
            MainActivity.request.executeRequest(request);
            return MainActivity.request.executeRequest(SQLrequest[0]);
        }

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> response) {
            if (response != null) {
                MainActivity.userCourant.addEmprunt(livre);
                button.setVisibility(View.INVISIBLE);
                MainActivityUsager.updateLists();
                finish();
            } else {
                Toast.makeText(getParent(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

    class RequestTaskRendre extends
            AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                String... SQLrequest) {
            String request = "UPDATE livre SET id_emprunteur = null" +
                    " WHERE id_livre = " + livre.getIdLivre();
            MainActivity.request.executeRequest(request);
            return MainActivity.request.executeRequest(SQLrequest[0]);
        }

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> response) {
            if (response != null) {
                MainActivity.userCourant.rendre(livre);
                button.setVisibility(View.INVISIBLE);
                MainActivityUsager.updateLists();
                finish();
            } else {
                Toast.makeText(getParent(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }
}
