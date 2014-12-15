package com.joris.bibliotheque.Main;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joris.bibliotheque.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity qui gère les inscriptions
 */
public class InscriptionActivity extends Activity {

    private EditText edit_nom;
    private EditText edit_prenom;
    private EditText edit_login;
    private EditText edit_mdp;
    private EditText edit_email;
    private String nom;
    private String prenom;
    private String login;
    private String mdp;
    private String email;
    private ProgressBar progressbar;
    private Button button_inscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        progressbar = (ProgressBar) findViewById(R.id.inscription_progress_bar);
        edit_nom = (EditText) findViewById(R.id.edit_nom);
        edit_prenom = (EditText) findViewById(R.id.edit_prenom);
        edit_login = (EditText) findViewById(R.id.edit_login);
        edit_mdp = (EditText) findViewById(R.id.edit_mdp);
        edit_email = (EditText) findViewById(R.id.edit_email);
        button_inscription = (Button) findViewById(R.id.bt_inscription);


        edit_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    hideSoftKeyBoardOnTabClicked(v);
                    inscription();
                    handled = true;
                }
                return handled;
            }
        });

        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        setFocusChange();
    }

    private void inscription() {
        if (recupererValeurs()) {
            if (isValidEmailAddress(email)) {
                button_inscription.setVisibility(View.GONE);
                String SQLrequest = "INSERT INTO usager (nom_usager, prenom_usager)" +
                        " VALUES ('" + nom + "', '" + prenom + "' )";

                new RequestTaskInscriptionUsager().execute(SQLrequest);
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_email), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.probleme_champs), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Retourne une string en md5
     *
     * @param md5
     *         String qu'on veut son MD5
     * @return Le MD5 du parametre md5
     */
    private String toMD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
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

        nom = edit_nom.getText().toString();
        nom = nom.replaceAll("'", "''");
        prenom = edit_prenom.getText().toString();
        prenom = prenom.replaceAll("'", "''");
        login = edit_login.getText().toString();
        login = login.replaceAll("'", "''");
        mdp = edit_mdp.getText().toString();
        email = edit_email.getText().toString();
        email = email.replaceAll("'", "''");

        if (nom.isEmpty() || prenom.isEmpty() || login.isEmpty() || mdp.isEmpty() || email.isEmpty())
            return false;

        return true;
    }

    /**
     * Test si une string est bien une adresse email
     *
     * @param email
     *         L'email à tester
     * @return boolean si c'est un email valide ou non
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Ferme le clavier quand la View v est cliqué
     *
     * @param v
     *         view qu'on vient de cliquer
     */
    private void hideSoftKeyBoardOnTabClicked(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * RequestTask pour l'inscription de l'usager
     */
    class RequestTaskInscriptionUsager extends
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
                String mdpMD5 = toMD5(mdp);

                String SQLrequest = "INSERT INTO user (email_user, login_user, pass_login_user, "
                        + "type_user, id_usager) "
                        + "VALUES ('" + email + "', '" + login + "', '" + mdpMD5 + "', '1', '"
                        + Integer.parseInt(response.get(0).get("id_res")) + "' )";

                new RequestTaskInscriptionUser().execute(SQLrequest);

            } else {
                button_inscription.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

    /**
     * RequestTask pour l'inscription de l'user
     */
    class RequestTaskInscriptionUser extends
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
                Toast.makeText(getApplicationContext(), getString(R.string.add_inscription), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.probleme_bdd), Toast.LENGTH_SHORT).show();
            }
            progressbar.setVisibility(View.GONE);
        }
    }

    /**
     * Va changer le text dans les edittext en fonction du focus
     */
    private void setFocusChange() {
        edit_nom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_nom.getText().toString())) {
                    edit_nom.setText(getString(R.string.edit_nom));
                } else if (hasFocus && edit_nom.getText().toString().equals(getString(R.string.edit_nom))) {
                    edit_nom.setText("");
                }
            }
        });

        edit_prenom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_prenom.getText().toString())) {
                    edit_prenom.setText(getString(R.string.edit_prenom));
                } else if (hasFocus && edit_prenom.getText().toString().equals(getString(R.string.edit_prenom))) {
                    edit_prenom.setText("");
                }
            }
        });

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
                    edit_mdp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    edit_mdp.setText(getString(R.string.edit_mdp));
                } else if (hasFocus && edit_mdp.getText().toString().equals(getString(R.string.edit_mdp))) {
                    edit_mdp.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    edit_mdp.setText("");
                }
            }
        });

        edit_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(edit_email.getText().toString())) {
                    edit_email.setText(getString(R.string.edit_email));
                } else if (hasFocus && edit_email.getText().toString().equals(getString(R.string.edit_email))) {
                    edit_email.setText("");
                }
            }
        });
    }
}
