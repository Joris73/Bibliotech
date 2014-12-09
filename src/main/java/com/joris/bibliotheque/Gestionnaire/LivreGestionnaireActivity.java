package com.joris.bibliotheque.Gestionnaire;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
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
import com.joris.bibliotheque.Usager.MainActivityUsager;

import java.util.ArrayList;
import java.util.HashMap;

public class LivreGestionnaireActivity extends Activity {

    private ProgressBar progressbar;
    private Button buttonModifier;
    private Button buttonSupprimer;
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
            TextView emprunter = (TextView) findViewById(R.id.tv_emprunter_par_gestionnaire);
            buttonModifier = (Button) findViewById(R.id.bt_modifier);
            buttonSupprimer = (Button) findViewById(R.id.bt_supprimer);

            titre.setText(livre.getTitre());
            annee.setText(Integer.toString(livre.getAnnee()));
            auteur.setText(livre.getAuteur());
            editeur.setText(livre.getEditeur());
            isbn.setText(Long.toString(livre.getISBN()));

            if (livre.isEmprunte()) {
            } else {
                emprunter.setText(getString(R.string.emprunt_personne));
            }

            buttonModifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new FragmentAjoutModifLivreGestionnaire();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id_livre", livre.getIdLivre());
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment)
                            .commit();
                    finish();
                }
            });

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
                buttonModifier.setVisibility(View.INVISIBLE);
                MainActivityUsager.updateLists();
                finish();
            } else {
                Toast.makeText(getParent(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }
}
