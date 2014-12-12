package com.joris.bibliotheque.Main;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.joris.bibliotheque.R;

/**
 * Activité qui gère les options de l'application
 */
public class OptionsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}