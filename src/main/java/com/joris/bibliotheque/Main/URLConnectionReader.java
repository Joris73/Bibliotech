package com.joris.bibliotheque.Main;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class URLConnectionReader {

	public static String lire(String urlString, String param,
			final String user, final String password)
			throws Exception {
		StringBuffer response = new StringBuffer();
		try {
			URL url = new URL(urlString+ "?" + param);
			//System.out.println(url.toString());
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			//connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");/*
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("charset", "utf-8");
			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(param.getBytes().length));
			connection.setUseCaches(false);*/

			Authenticator.setDefault(new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password
							.toCharArray());
				}
			});
			/*
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(param);
			wr.flush();
			wr.close();*/
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF8"));
			
			String inputLine;
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
		} catch (Exception exception) {
			Log.i("URLConnectionReader",
					"Gros probl�me: " + exception.getMessage());
		}
		return response.toString();
	}
}
