package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_device_list.view.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_favorites.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.fragments.recyclerViewAdapters.DevicesAdapter
import org.hogent.phonelibrary.fragments.recyclerViewAdapters.FavoriteDevicesAdapter
import org.hogent.phonelibrary.viewModels.FavoriteDevicesViewModel

/**
 * Fragment to show the favorite devices.
 *
 */
class FavoritesFragment : Fragment() {
    private var listener: OnDeviceSelectedListener? = null

    private lateinit var favoriteDevicesViewModel: FavoriteDevicesViewModel

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

        // Return the view.
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the favorites.
        favoriteDevicesViewModel.favoriteDevices
            .observe(this, Observer {
                // Load the result into the adapter.
                if (it != null && it.count() > 0) {
                    // Push devices to adapter.
                    (favoritesRecyclerView.adapter as FavoriteDevicesAdapter).setFavoriteDevices(it)
                    // Update fragment title.
                    listener!!.updateTitle("${getString(R.string.title_activity_fragment_favorites)} (${it.count()})")
                } else {
                    // todo placeholder for no devices.
                    // Update title. Putting in the OnAttach overwrites a successful result.
                    listener!!.updateTitle(getString(R.string.title_activity_fragment_favorites))
                }
            })
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         */
        @JvmStatic
        fun newInstance() = FavoritesFragment()
    }
}
