package org.hogent.phonelibrary.fragments.recyclerViewAdapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_VIEW_END
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.device_category_list_row.view.*
import kotlinx.android.synthetic.main.device_spec_item.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.SpecCategory
import org.hogent.phonelibrary.domain.models.specs.StringSpec


class SpecCategoriesAdapter : RecyclerView.Adapter<SpecCategoriesAdapter.CategoryHolder>() {

    // The list of categories.
    private var specCategories: List<SpecCategory> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_category_list_row, parent, false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int = specCategories.count()

    override fun onBindViewHolder(categoryHolder: CategoryHolder, index: Int) {
        val specCategory = specCategories[index]
        // Set values of holder.
        categoryHolder.categoryName.text = specCategory.identifier.toString()

        // Clear the specs container. Contains a placeholder for xml preview.
        categoryHolder.specsContainer.removeAllViewsInLayout()

        // Make a spec view for every spec.
        specCategory.specs.forEach {
            // Decide what type of spec it is.
            if (it is StringSpec) {
                addStringSpecLayoutItem(it, categoryHolder.specsContainer)
            } else {
                Log.e("Spec view", "Spec of type ${it.getType().name} is not supported for visualization.")
            }
        }
    }

    /**
     * A view corresponding to a spec item of the string type.
     *
     * @param stringSpec
     * @param parent
     */
    private fun addStringSpecLayoutItem(stringSpec: StringSpec, parent: ViewGroup) {
        val screenWidth = parent.context.resources.displayMetrics.widthPixels
        val ct = parent.context

        val inflater = LayoutInflater.from(ct)
        val specItemGroup = inflater.inflate(R.layout.device_spec_item, parent, false) as ViewGroup

        // Set text values.
        specItemGroup.textViewDeviceSpecName.text = stringSpec.getDisplayName()
        specItemGroup.textViewDeviceSpecValue.text = stringSpec.getValue()

        // Set width values. Max width is half of the screen.
        specItemGroup.textViewDeviceSpecName.maxWidth = screenWidth / 2
        specItemGroup.textViewDeviceSpecValue.maxWidth = screenWidth / 2

        parent.specsContainer.addView(specItemGroup)
    }

    /**
     * Set the spec categories. Not in constructor for optional async loading.
     *
     * @param specCategories
     */
    fun setSpecCategories(specCategories: List<SpecCategory>) {
        this.specCategories = specCategories
        notifyDataSetChanged()
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.categoryTextView
        val specsContainer: LinearLayout = view.specsContainer
    }
}