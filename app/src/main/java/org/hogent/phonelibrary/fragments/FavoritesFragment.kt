package org.hogent.phonelibrary.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.viewModels.FavoriteDevicesViewModel

// todo FavoritesFragment class documentation
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
                    // todo set adapter
                    // Update fragment title.
                    listener!!.updateTitle("${getString(R.string.title_activity_fragment_favorites)} (${it.count()})")
                } else {
                    // Update title. Putting in the OnAttach overwrites a successful result.
                    listener!!.updateTitle(getString(R.string.title_activity_fragment_favorites))
                }
            })
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
