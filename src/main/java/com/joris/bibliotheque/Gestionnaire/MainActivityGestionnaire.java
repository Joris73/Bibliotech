package com.joris.bibliotheque.Gestionnaire;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.joris.bibliotheque.R;

/**
 * Activité principal du gestionnaire
 */
public class MainActivityGestionnaire extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public static Integer id_livre = null;
    private final static String FRAGMENT_TAG_LIST = "ListeLivreFragment_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_gestionnaire);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {
            case 0:
                mTitle = getString(R.string.title_livres);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new FragmentListeLivresGestionnaire(), FRAGMENT_TAG_LIST)
                        .commit();
                break;
            case 1:
                mTitle = getString(R.string.title_add);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new FragmentAjoutModifLivreGestionnaire())
                        .commit();
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_activity_gestionnaire, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * On va ici mettre à jour les infos de l'activity à son retour
     */
    @Override
    public void onResume() {
        super.onResume();
        FragmentListeLivresGestionnaire mitemListFragment = (FragmentListeLivresGestionnaire)
                getFragmentManager().findFragmentByTag(FRAGMENT_TAG_LIST);
        if (mitemListFragment != null)
            mitemListFragment.updateList();

        if (id_livre != null) {
            mTitle = getString(R.string.title_modifier);
            getActionBar().setTitle(mTitle);
            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragment = new FragmentAjoutModifLivreGestionnaire();
            Bundle bundle = new Bundle();
            bundle.putInt("id_livre", id_livre);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            id_livre = null;
        }
    }
}
