package com.joris.bibliotheque.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Classes.Usager;
import com.joris.bibliotheque.Gestionnaire.MainActivityGestionnaire;
import com.joris.bibliotheque.R;
import com.joris.bibliotheque.Usager.MainActivityUsager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activité principal qui gère la connexion et la récupération de toutes la base de livre
 */
public class MainActivity extends Activity {

    static public ArrayList<Livre> listeLivre;
    static public Usager userCourant;
    public static Requests request;
    private ProgressBar progressbar;
    private EditText edit_login;
    private EditText edit_mdp;
    private String login;
    private String mdp;
    private boolean isGestionnaire = false;
    final private String URL_API = "http://78.238.140.91/Bibliotheque/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = new Requests(URL_API,
                "appli", "root");

        listeLivre = new ArrayList<>();

        progressbar = (ProgressBar) findViewById(R.id.main_progress_bar);
        edit_login = (EditText) findViewById(R.id.edit_connect_login);
        edit_mdp = (EditText) findViewById(R.id.edit_connect_mdp);
        Button button_connexion = (Button) findViewById(R.id.bt_connexion);
        Button button_inscription = (Button) findViewById(R.id.bt_main_inscription);

        button_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recupererValeurs()) {

                    String mdpMD5 = toMD5(mdp);

                    String SQLrequest = "SELECT * "
                            + "FROM user U "
                            + "JOIN usager US ON U.id_usager=US.id_usager "
                            + "WHERE U.login_user = '" + login + "' and pass_login_user = '" + mdpMD5 + "'";

                    new RequestTaskConnexion().execute(SQLrequest);

                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.probleme_champs), Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(intent);
            }
        });

        setFocusChange();
    }

    /**
     * Retourne une string en md5
     *
     * @param md5
     * @return
     */
    public String toMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * @return vrai si champs valide, faux sinon
     */
    private boolean recupererValeurs() {
        login = edit_login.getText().toString();
        login = login.replaceAll("'", "''");
        mdp = edit_login.getText().toString();

        if (login.isEmpty() || mdp.isEmpty())
            return false;

        return true;
    }

    /**
     * Va changer le text dans les edittext en fonction du focus
     */
    private void setFocusChange() {
        edit_login.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_login.getText().toString())) {
                    edit_login.setText(getString(R.string.edit_login));
                } else if (hasFocus && edit_login.getText().toString().equals(getString(R.string.edit_login))) {
                    edit_login.setText("");
                }
            }
        });

        edit_mdp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_mdp.getText().toString())) {
                    edit_mdp.setText(getString(R.string.edit_mdp));
                } else if (hasFocus && edit_mdp.getText().toString().equals(getString(R.string.edit_mdp))) {
                    edit_mdp.setText("");
                }
            }
        });
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

    /**
     * RequestTask qui s'occupe de la connexion
     */
    class RequestTaskConnexion extends
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
                if (!response.isEmpty()) {
                    for (HashMap<String, String> reuser : response) {
                        userCourant = new Usager(Integer.parseInt(reuser.get("id_usager")), reuser.get("login_user"),
                                reuser.get("nom_usager"), reuser.get("prenom_usager"), reuser.get("email_user"));

                        if (Integer.parseInt(reuser.get("type_user")) == 1) {
                            isGestionnaire = false;
                        } else {
                            isGestionnaire = true;
                        }
                    }
                    String SQLrequest = "SELECT * "
                            + "FROM livre L "
                            + "WHERE L.deleted = 0";

                    new RequestTaskAllLivre().execute(SQLrequest);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.probleme_login), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

    /**
     * RequestTask qui s'occupe de récupérer tous les livres et s'ils sont emprunté par
     * le user connecté sont rajouté à sa liste.
     */
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

                if (isGestionnaire) {
                    Intent intent = new Intent(getApplicationContext(), MainActivityGestionnaire.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivityUsager.class);
                    startActivity(intent);
                }

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }
}
