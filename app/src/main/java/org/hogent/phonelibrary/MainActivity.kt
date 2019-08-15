package org.hogent.phonelibrary

import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import org.hogent.phonelibrary.domain.models.Device
import org.hogent.phonelibrary.fragments.*
import org.hogent.phonelibrary.viewModels.FavoriteDevicesViewModel
import org.hogent.phonelibrary.viewModels.MainActivityViewModel
import org.hogent.phonelibrary.viewModels.SuccessResult
import java.lang.Exception

private const val BACK_STACK_ROOT_TAG = "root_fragment"

class MainActivity : AppCompatActivity(), IParentActivity, OnDeviceSelectedListener,
    SearchFragment.OnDevicesLookupResultsListener {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private var isTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialise the view model.
        mainActivityViewModel = ViewModelProviders.of(this)
            .get(MainActivityViewModel::class.java)

        // Check if there are two panes.
        isTwoPane = (fragment_container != null) && (secondFragment_container != null)
        Log.i("Is two pane", isTwoPane.toString())

        //Configure navigation view. Set the listener.
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (!(isTwoPane || mainActivityViewModel.isTwoPane)) {
            // Check if bundle is empty, meaning this is the first time the activity was launched.
            if (savedInstanceState == null) {
                //Set the SearchFragment as the default fragment on startup.
                navView.selectedItemId = R.id.navigation_search
            }
        } else {
            // Was or is two-pane.
            // Always reset the screens when going to or coming from a two-pane. Two-panes start on another navigation item.)
            navView.selectedItemId = if (isTwoPane) R.id.navigation_home else R.id.navigation_search
        }

        // Update view model.
        mainActivityViewModel.isTwoPane = isTwoPane

        // Update the visibility of the back button in the action bar.
        updateActionBarBackButton()
    }

    /**
     *  Listener for the bottom bar.
     */
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            // Home (two pane) or search both equal home screen.
            R.id.navigation_search, R.id.navigation_home -> {
                switchFragment(FragmentNames.SEARCH, SearchFragment.newInstance())
            }
            R.id.navigation_favorites -> {
                switchFragment(FragmentNames.FAVORITES, FavoritesFragment.newInstance())
            }
            else ->
                return@OnNavigationItemSelectedListener false
        }
        return@OnNavigationItemSelectedListener true
    }

    /**
     * Back press detection in action bar.
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Call function to go back.
                onBackPressed()
                return true
            }
        }
        // Else call parent.
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (isTwoPane) {
            // Two pane back press resets to home screen.
            switchFragment(FragmentNames.SEARCH, null)
        } else {
            super.onBackPressed()
        }

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

// OnDevicesLookupResultsListener

    override fun onDevicesLookupResultsFound() {
        //Load the list fragment.
        switchFragment(FragmentNames.LIST, DeviceListFragment.newInstance())
    }

// OnDeviceSelectedListener

    override fun onDeviceSelection(device: Device) {
        //Switch to the detail fragment.
        switchFragment(FragmentNames.DETAIL, DeviceDetailFragment.newInstance(device))
    }

// IParentActivity functions

    override fun showToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    override fun updateTitle(title: String) {
        if (!isTwoPane) {
            supportActionBar?.title = title
        }
    }

    /**
     * Helper function to show a new fragment on the screen.
     * If the fragment is a root fragment. Pressing the back button will close the activity.
     * If the fragment is not a root fragment, a back option will be available.
     *
     * @param fragment The fragment to switch to.
     */
    private fun switchFragment(fragment: FragmentNames, fragmentInstance: Fragment?) {
        Log.i(
            "Switch fragment",
            "Switching to fragment $fragment."
        )

        try {
            // Check whether or not it's a root fragment.
            if (fragment == FragmentNames.FAVORITES || fragment == FragmentNames.SEARCH) {
                // It is a root fragment.

                // Completely Clear back stack, including the root fragment.
                supportFragmentManager.popBackStack(BACK_STACK_ROOT_TAG, POP_BACK_STACK_INCLUSIVE)

                if (!isTwoPane) {
                    // Show fragment.
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragmentInstance!!)
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit()
                } else {
                    // Show the two root fragments.
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, SearchFragment.newInstance())
                        .replace(R.id.secondFragment_container, FavoritesFragment.newInstance())
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit()
                }
            } else {
                // It's not a root fragment.

                if (!isTwoPane) {
                    // Show fragment.
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragmentInstance!!)
                        .addToBackStack(null)
                        .commit()
                } else {
                    // Update the two panes based on the fragment to show.
                    updateTwoPanes(fragment, fragmentInstance!!)
                }
            }
            // Force execution of transactions for back stack on main thread.
            // Necessary to calculate the amount of items of the back stack,
            // adjusting the back button in the activity's action bar.
            supportFragmentManager.executePendingTransactions()
        } catch (ex: Exception) {
            // This exception can apparently be null.
            Log.e("Switch fragment", ex.message)
        }
        updateActionBarBackButton()
    }

    /**
     * Updates the two panes based on the fragment to switch to.
     *
     * @param fragment
     */
    private fun updateTwoPanes(fragment: FragmentNames, fragmentInstance: Fragment) {
        when (fragment) {
            FragmentNames.LIST -> {
                // LIST: left pane becomes list, right one becomes placeholder fragment.
                supportFragmentManager
                    .beginTransaction()
                    // Left
                    .replace(R.id.secondFragment_container, fragmentInstance)
                    // Right
                    .replace(R.id.fragment_container, PlaceHolderFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            FragmentNames.DETAIL -> {
                // DETAIL: left pane stays the same, right one becomes detail fragment.
                supportFragmentManager
                    .beginTransaction()
                    // Right
                    .replace(R.id.fragment_container, fragmentInstance)
                    .commit()
            }
            else -> {
                Log.w("Switch fragment", "Unsupported fragment to switch to for double pane: $fragment")
            }
        }
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

    enum class FragmentNames {
        FAVORITES,
        SEARCH,
        LIST,
        DETAIL,
        PLACEHOLDER
    }
}

interface IParentActivity {
    /**
     * Helper function for showing a toast.
     *
     * @param text The text to show.
     */
    fun showToast(text: String)

    /**
     * Updates the title in the action bar.
     *
     * @param title The new title.
     */
    fun updateTitle(title: String)
}