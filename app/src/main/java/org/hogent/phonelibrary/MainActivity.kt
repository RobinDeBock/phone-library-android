package org.hogent.phonelibrary

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.hogent.phonelibrary.fragments.FavoritesFragment
import org.hogent.phonelibrary.fragments.SearchFragment

class MainActivity : AppCompatActivity(), FavoritesFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentToSwitchTo: Fragment =
            when (item.itemId) {
                R.id.navigation_search -> {
                    SearchFragment.newInstance()
                }
                R.id.navigation_favorites -> {
                    FavoritesFragment.newInstance()
                }
                else ->
                    return@OnNavigationItemSelectedListener false
            }

        switchFragment(fragmentToSwitchTo)
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configure navigation view. Set the listener.
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //Set the org.hogent.phonelibrary.fragments.SearchFragment as the default fragment on startup.
        switchFragment(SearchFragment.newInstance())
    }

    /**
     * Helper function to show a new fragment on the screen.
     *
     * @param fragment The fragment to switch to.
     */
    private fun switchFragment(fragment: Fragment) {
        //Write log.
        Log.i("Switch fragment", "Switching to fragment ${fragment::class.simpleName}")

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
