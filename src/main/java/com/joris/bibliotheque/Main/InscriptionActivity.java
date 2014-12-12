package com.joris.bibliotheque.Main;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joris.bibliotheque.R;

public class InscriptionActivity extends Activity {

    private EditText edit_nom;
    private EditText edit_prenom;
    private EditText edit_login;
    private EditText edit_mdp;
    private EditText edit_email;
    private String login;
    private String mdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        edit_nom = (EditText) findViewById(R.id.edit_nom);
        edit_prenom = (EditText) findViewById(R.id.edit_prenom);
        edit_login = (EditText) findViewById(R.id.edit_login);
        edit_mdp = (EditText) findViewById(R.id.edit_mdp);
        edit_email = (EditText) findViewById(R.id.edit_email);
        Button button_inscription = (Button) findViewById(R.id.bt_inscription);

        button_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recupererValeurs()) {

                    String mdpMD5 = toMD5(mdp);

                    String SQLrequest = "SELECT * "
                            + "FROM user U "
                            + "JOIN usager US ON U.id_usager=US.id_usager "
                            + "WHERE U.login_user = '" + login + "' and pass_login_user = '" + mdpMD5 + "'";

                    //new RequestTaskConnexion().execute(SQLrequest);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.probleme_champs), Toast.LENGTH_SHORT).show();
                }
            }
        });

        setFocusChange();
    }

    /**
     * Retourne une string en md5
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
                    edit_mdp.setText(getString(R.string.edit_mdp));
                } else if (hasFocus && edit_mdp.getText().toString().equals(getString(R.string.edit_mdp))) {
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
