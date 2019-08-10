package org.hogent.phonelibrary

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.fragments.*
import org.hogent.phonelibrary.viewModels.SuccessResult
import java.lang.Exception

private const val BACK_STACK_ROOT_TAG = "root_fragment"

class MainActivity : AppCompatActivity(), ParentActivity, OnDeviceSelectedListener,
    SearchFragment.OnDevicesLookupResultsListener {

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

    override fun onDevicesLookupResultsFound(successResult: SuccessResult) {
        //Load the favorites fragment.
        switchFragment(DeviceListFragment.newInstance(), false)
    }

    override fun onDeviceSelection(device : Device) {
        //Switch to the detail fragment.
        switchFragment(DeviceDetailFragment.newInstance(device), false)
    }

    override fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configure navigation view. Set the listener.
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Update the visibility of the back button in the action bar.
        updateActionBarBackButton()

        //Check if bundle is empty, meaning this is the first time the activity was launched.
        if (savedInstanceState == null) {
            //Set the org.hogent.phonelibrary.fragments.SearchFragment as the default fragment on startup.
            navView.selectedItemId = R.id.navigation_search
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // If the back stack has no fragments, close the activity.
        if (supportFragmentManager.backStackEntryCount < 1) {
            Log.i(
                "Empty back stack",
                "Closing back stack due to pressing back while the back stack is empty."
            )
            // Close the activity.
            this.finish()
        }
        updateActionBarBackButton()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Call parent function to go back.
                super.onBackPressed()
                // Back button in action bar was pressed.
                updateActionBarBackButton()
                return true
            }
        }
        // Else call parent.
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

        try {
            // Check whether or not it's a root fragment.
            if (isNavigationalRoot) {
                // It is a root fragment.

                // Completely Clear back stack, including the root fragment.
                supportFragmentManager.popBackStack(BACK_STACK_ROOT_TAG, POP_BACK_STACK_INCLUSIVE)

                // Show fragment.
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
            } else {
                // It's not a root fragment.

                // Add fragment to fragment back stack.
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            // Force execution of transactions for back stack on main thread.
            // Necessary to calculate the amount of items of the back stack,
            // adjusting the back button in the activity's action bar.
            supportFragmentManager.executePendingTransactions()
        } catch (ex: Exception) {
            Log.e("Switch fragment", ex.message)
        }
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

interface ParentActivity {
    /**
     * Helper function for showing a toast.
     *
     * @param text The text to show.
     */
    fun showToast(text: String)
}