package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.fragments.recyclerViewAdapters.FavoriteDevicesAdapter
import org.hogent.phonelibrary.viewModels.FavoriteDevicesViewModel
import android.content.SharedPreferences
import org.hogent.phonelibrary.domain.models.Device


private const val SORT_PREF_NAME = "isSortedByBrand"
private const val PRIVATE_MODE = 0

/**
 * Fragment to show the favorite devices.
 *
 */
class FavoritesFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var listener: OnDeviceSelectedListener? = null

    private lateinit var favoriteDevicesViewModel: FavoriteDevicesViewModel

    private var sortByBrand: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Check that parent activity implements required interface.
        if (context is OnDeviceSelectedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ${OnDeviceSelectedListener::class.simpleName}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialise the view model.
        favoriteDevicesViewModel = ViewModelProviders.of(this)
            .get(FavoriteDevicesViewModel::class.java)
        // Get the last sort option from the shared preferences and store into variable.
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(SORT_PREF_NAME, PRIVATE_MODE)
        sortByBrand = sharedPref.getBoolean(SORT_PREF_NAME, false) //Default value is false.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        // Set the adapter and layout manager of the recycler view.
        view.favoritesRecyclerView.adapter = FavoriteDevicesAdapter(listener!!)
        view.favoritesRecyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayout.VERTICAL, false)

        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter.createFromResource(
            context!!,
            R.array.device_sort_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            view.sortChoice.adapter = adapter
            // Set the selected index.
            view.sortChoice.setSelection(if (sortByBrand) 1 else 0)
        }

        // Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoader(true)
        // Observe the favorites.
        favoriteDevicesViewModel.favoriteDevices
            .observe(this, Observer {
                // Load the result into the adapter.
                if (it != null && it.count() > 0) {
                    handleFavoriteDevices(it)
                } else {
                    handleNoFavoriteDevices()
                }
            })

        // Add listener to spinner.
        sortChoice.onItemSelectedListener = this
    }

    private fun showLoader(visible: Boolean) {

    }

    /**
     * Handles the list of favorite devices if count > 1.
     *
     * @param devices
     */
    private fun handleFavoriteDevices(devices: List<Device>) {
        // Hide placeholder for no devices.
        noFavoritesPlaceholder.visibility = View.GONE
        // Push devices to adapter. Check what option was selected in the sort choice spinner.
        (favoritesRecyclerView.adapter as FavoriteDevicesAdapter).setFavoriteDevices(
            devices,
            sortByBrand
        )
        // Update fragment title.
        listener!!.updateTitle("${getString(R.string.title_activity_fragment_favorites)} (${devices.count()})")
    }

    /**
     * Handles if the amount of favorite devices == 0.
     *
     */
    private fun handleNoFavoriteDevices() {
        // Placeholder for no devices.
        noFavoritesPlaceholder.visibility = View.VISIBLE
        // Update title. Putting in the OnAttach overwrites a successful result.
        listener!!.updateTitle(getString(R.string.title_activity_fragment_favorites))
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Remove observer from favorite devices.
        favoriteDevicesViewModel.favoriteDevices.removeObservers(this)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * An item is selected in the spinner.
     *
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // The first option is sorting by name.
        sortByBrand = position == 1
        // Update the shared preference.
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(SORT_PREF_NAME, PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putBoolean(SORT_PREF_NAME, sortByBrand)
        editor.apply()
        // Update the list of devices.
        (favoritesRecyclerView.adapter as FavoriteDevicesAdapter).updateSorting(sortByBrand)
    }

    /**
     * Nothing is selected in the spinner. Ignored.
     *
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.w("Spinner selection", "Nothing is selected in the favorites sort spinner. This gets ignored though.")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         */
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}
