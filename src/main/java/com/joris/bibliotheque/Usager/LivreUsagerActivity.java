package com.joris.bibliotheque.Usager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.R;

public class LivreUsagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livre_usager);
        Intent intent = getIntent();
        if (intent != null) {
            int id = intent.getIntExtra("idLivre", 0);
            final Livre livre = Livre.GetLivreList(MainActivityUsager.listeLivre, id);
            TextView titre = (TextView) findViewById(R.id.tvTitre);
            TextView annee = (TextView) findViewById(R.id.tv_annee);
            TextView auteur = (TextView) findViewById(R.id.tv_auteur);
            TextView editeur = (TextView) findViewById(R.id.tv_editeur);
            TextView isbn = (TextView) findViewById(R.id.tv_isbn);
            final Button button = (Button) findViewById(R.id.btEmpRend);

            titre.setText(livre.getTitre());
            annee.setText(Integer.toString(livre.getAnnee()));
            auteur.setText(livre.getAuteur());
            editeur.setText(livre.getEditeur());
            isbn.setText(Integer.toString(livre.getISBN()));

            if (livre.isEmprunte()) {
                if (livre.getEmpruntePar().getIdUsager() == MainActivityUsager.userCourant.getIdUsager()) {
                    button.setText(getString(R.string.bt_rendre));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivityUsager.userCourant.rendre(livre);
                            button.setVisibility(View.INVISIBLE);
                            MainActivityUsager.updateLists();
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
                        MainActivityUsager.userCourant.addEmprunt(livre);
                        button.setVisibility(View.INVISIBLE);
                        MainActivityUsager.updateLists();
                    }
                });
            }
        }
    }
}
