package com.joris.bibliotheque.Main;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

/**
 * Classe de lecture d'une URL
 */
public class URLConnectionReader {

    public static String lire(String urlString, String param,
                              final String user, final String password)
            throws Exception {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(urlString + "?" + param);
            Log.i("Requete", url.toString());
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password
                            .toCharArray());
                }
            });

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), "UTF8"));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
        } catch (Exception exception) {
            Log.i("URLConnectionReader", "Gros problème: " + exception.getMessage());
        }
        return response.toString();
    }
}
