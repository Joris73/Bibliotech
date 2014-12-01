package com.joris.bibliotheque.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.joris.bibliotheque.Classes.Livre;
import com.joris.bibliotheque.Classes.Usager;
import com.joris.bibliotheque.Gestionnaire.MainActivityGestionnaire;
import com.joris.bibliotheque.R;
import com.joris.bibliotheque.Usager.MainActivityUsager;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button button_gestionnaire;
    private Button button_usager;
    static public ArrayList<Livre> listeLivre;
    static public Usager userCourant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_gestionnaire = (Button) findViewById(R.id.bt_gestionnaire);
        button_usager = (Button) findViewById(R.id.bt_usager);

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

        listeLivre = new ArrayList<Livre>();
        
        userCourant = new Usager(1, "bodinj", "bodin", "joris");

        /*
        listeLivre = new ArrayList<Livre>();
        Livre livre1 = new Livre("3", 1, "Toto à la plage 3", "Toto", "Hachette", 2002, null, null);
        Livre livre2 = new Livre("5", 1, "Toto à la plage 5", "Toto", "Hachette", 2004, null, null);

        listeLivre.add(new Livre("1", 1, "Toto à la plage", "Toto", "Hachette", 2000, null, null));
        listeLivre.add(new Livre("2", 1, "Toto à la plage 2", "Toto", "Hachette", 2001, null, null));
        listeLivre.add(livre1);
        listeLivre.add(new Livre("4", 1, "Toto à la plage 4", "Toto", "Hachette", 2003, null, null));
        listeLivre.add(livre2);

        userCourant = new Usager(1, "bodinj", "bodin", "joris");
        userCourant.addEmprunt(livre1);
        //userCourant.addEmprunt(livre2);

        Usager userCourant2 = new Usager(2, "bodinj", "bodin", "joris");
        userCourant2.addEmprunt(livre2);*/

        new mongodbTest().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class mongodbTest extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                MongoClient mongoClient = new MongoClient("192.168.0.15");
                DB db = mongoClient.getDB("test");
                Log.wtf("OUAAAAAAAAAAA", db.getCollectionNames().toString());
                DBCollection coll = db.getCollection("biblio");
                DBCursor cursor = coll.find();
                while (cursor.hasNext()) {
                    DBObject livre = cursor.next();
                    Double double1 = Double.parseDouble(livre.get("ISBN").toString());
                    Double double2 = Double.parseDouble(livre.get("annee").toString());


                    listeLivre.add(new Livre(livre.get("_id").toString(), double1.intValue(),
                            livre.get("titre").toString(), livre.get("auteur").toString()
                            , livre.get("editeur").toString(), double2.intValue(), null, null));
                }
            } catch (UnknownHostException e) {
                Log.wtf("FUCK", e.toString());
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            button_gestionnaire.setVisibility(View.VISIBLE);
            button_usager.setVisibility(View.VISIBLE);
        }
    }
}
