package com.joris.bibliotheque.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Classes.Usager;
import com.joris.bibliotheque.Gestionnaire.MainActivityGestionnaire;
import com.joris.bibliotheque.R;
import com.joris.bibliotheque.Usager.MainActivityUsager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    private Button button_gestionnaire;
    private Button button_usager;
    static public ArrayList<Livre> listeLivre;
    static public Usager userCourant;
    public static Requests request;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        request = new Requests("http://78.238.140.91//Bibliotheque/api/",
                "appli", "root");

        listeLivre = new ArrayList<>();
        userCourant = new Usager(1, "bodinj", "bodin", "joris");

        button_gestionnaire = (Button) findViewById(R.id.bt_gestionnaire);
        button_usager = (Button) findViewById(R.id.bt_usager);
        progressbar = (ProgressBar) findViewById(R.id.main_progress_bar);

        button_gestionnaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityGestionnaire.class);
                startActivity(intent);
            }
        });
        button_gestionnaire.setVisibility(View.GONE);

        button_usager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityUsager.class);
                startActivity(intent);
            }
        });
        button_usager.setVisibility(View.GONE);

        String SQLrequest = "SELECT * "
                + "FROM livre L "
                + "WHERE L.deleted = 0";

        new RequestTaskAllLivre().execute(SQLrequest);
    }
    public String toMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            return new String(array, "UTF-8");
        } catch (java.security.NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class RequestTaskAllLivre extends
            AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(
                String... SQLrequest) {
            return request.executeRequest(SQLrequest[0]);
        }

        @Override
        protected void onPreExecute() {
            progressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> response) {
            if (response != null) {
                listeLivre.clear();
                Livre livre;
                for (HashMap<String, String> relivre : response) {

                    if (relivre.get("id_emprunteur") != null) {
                        int idUsager = Integer.parseInt(relivre.get("id_emprunteur"));
                        livre = new Livre(Integer.parseInt(relivre.get("id_livre")),
                                Long.parseLong(relivre.get("ISBN")), relivre.get("titre_livre"),
                                relivre.get("auteur_livre"), relivre.get("editeur_livre"),
                                Integer.parseInt(relivre.get("annee_livre")),
                                relivre.get("description_livre"), null, idUsager);
                        if (idUsager == userCourant.getIdUsager())
                            userCourant.addEmprunt(livre);
                    } else {
                        livre = new Livre(Integer.parseInt(relivre.get("id_livre")),
                                Long.parseLong(relivre.get("ISBN")), relivre.get("titre_livre"),
                                relivre.get("auteur_livre"), relivre.get("editeur_livre"),
                                Integer.parseInt(relivre.get("annee_livre")),
                                relivre.get("description_livre"), null, null);
                    }
                    listeLivre.add(livre);
                }
            } else {
                Toast.makeText(getParent(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            button_gestionnaire.setVisibility(View.VISIBLE);
            button_usager.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }
    }
}
