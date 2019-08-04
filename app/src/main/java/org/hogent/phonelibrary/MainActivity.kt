package org.hogent.phonelibrary

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import org.hogent.phonelibrary.fragments.DeviceDetailFragment
import org.hogent.phonelibrary.fragments.FavoritesFragment
import org.hogent.phonelibrary.fragments.OnDeviceSelectedListener
import org.hogent.phonelibrary.fragments.SearchFragment

private const val BACK_STACK_ROOT_TAG = "root_fragment"

class MainActivity : AppCompatActivity(), OnDeviceSelectedListener, SearchFragment.OnDevicesLookupResultsListener {

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

        switchFragment(fragmentToSwitchTo, true)
        return@OnNavigationItemSelectedListener true
    }

    override fun onDeviceslookupResultsFound() {
        //Load the favorites fragment.
        switchFragment(FavoritesFragment.newInstance(), false)
    }

    override fun onDeviceSelection() {
        //Switch to the detail fragment.
        switchFragment(DeviceDetailFragment.newInstance(), false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configure navigation view. Set the listener.
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //Set the org.hogent.phonelibrary.fragments.SearchFragment as the default fragment on startup.
        navView.selectedItemId = R.id.navigation_search
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //If the back stack has one or less fragments, close the activity.
        if (supportFragmentManager.backStackEntryCount < 1) {
            Log.i(
                "Empty back stack",
                "Closing back stack due to pressing back while the back stack is empty."
            )
            //Close the activity.
            this.finish()
        }
        updateActionBarBackButton()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                //Call parent function to go back.
                super.onBackPressed()
                //Back button in action bar was pressed.
                updateActionBarBackButton()
                return true
            }
        }
        //Else call parent.
        return super.onOptionsItemSelected(item)
    }

    /**
     * Helper function to show a new fragment on the screen.
     * If the fragment is a root fragment. Pressing the back button will close the activity.
     * If the fragment is not a root fragment, a back option will be available.
     *
     * @param fragment The fragment to switch to.
     * @param isNavigationalRoot Whether or not the fragment is a root fragment
     */
    private fun switchFragment(fragment: Fragment, isNavigationalRoot: Boolean) {
        Log.i(
            "Switch fragment",
            "Switching to fragment ${fragment::class.simpleName}. IsNavigationalRoot: $isNavigationalRoot"
        )

        //Check whether or not it's a root fragment.
        if (isNavigationalRoot) {
            //It is a root fragment.

            //Completely Clear back stack, including the root fragment.
            supportFragmentManager.popBackStack(BACK_STACK_ROOT_TAG, POP_BACK_STACK_INCLUSIVE)

            //Show fragment.
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit()
        } else {
            //It's not a root fragment.
            //Show back option and add fragment to fragment back stack.
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        //Force execution of transactions for back stack on main thread.
        supportFragmentManager.executePendingTransactions()
        updateActionBarBackButton()
    }

    /**
     * Updates the visibility of the action bar back button,
     * based on the contents of the fragment back stack.
     *
     */
    private fun updateActionBarBackButton() {
        val amount = supportFragmentManager.backStackEntryCount

        //Check if the top-stack fragment is a root fragment.
        if (amount > 0) {
            if (supportFragmentManager.getBackStackEntryAt(amount - 1).name == BACK_STACK_ROOT_TAG) {
                //Root fragment, hide back button.
                supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                return
            }
        }
        //Not a root fragment, show the back button.
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
