package com.joris.bibliotheque.Main;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe qui fait une demande à l'api puis transforme le json en une liste java
 * Si le résultat n'est pas du json, alors c'est l'id retour d'une insertion
 */
public class Requests {

    protected String host;
    protected String user;
    protected String password;

    public Requests(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public ArrayList<HashMap<String, String>> executeRequest(String SQLrequest) {
        // Log.i("Requete SQL: ", SQLrequest);
        ArrayList<HashMap<String, String>> result = null;
        try {
            String res = URLConnectionReader.lire(this.host + "requete.php",
                    "requete=" + URLEncoder.encode(SQLrequest, "UTF-8"),
                    this.user, this.password);

            Type listType = new TypeToken<ArrayList<HashMap<String, String>>>() {
            }.getType();
            Gson gson = new Gson();
            try {
                result = gson.fromJson(res, listType);

            } catch (Exception e) {
                try {
                    Log.wtf("Probl�me GSON", res);
                    res = res.replace(" [[\"", "");
                    res = res.replace("\"]]", "");
                    HashMap<String, String> hash = new HashMap<>();
                    hash.put("id_res", res);
                    result = new ArrayList<>();
                    result.add(hash);
                    return result;
                } catch (Exception e2) {
                    throw e2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
