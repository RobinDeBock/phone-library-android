package org.hogent.phonelibrary.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_favorites.view.*

import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.Device

// todo FavoritesFragment class documentation
class FavoritesFragment : Fragment() {
    private var listener: OnDeviceSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Check that parent activity implements required interface.
        if (context is OnDeviceSelectedListener) {
            listener = context
            // Set the title.
            listener!!.updateTitle(getString(R.string.title_activity_fragment_favorites))
        } else {
            throw RuntimeException("$context must implement ${OnDeviceSelectedListener::class.simpleName}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        // Add listener on button click.
        view.favorites_detail_button.setOnClickListener{
            listener!!.onDeviceSelection(Device())
        }

        // Return the view.
        return view
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
