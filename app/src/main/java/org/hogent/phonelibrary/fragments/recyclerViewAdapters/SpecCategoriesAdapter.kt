package org.hogent.phonelibrary.fragments.recyclerViewAdapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_VIEW_END
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.device_category_list_row.view.*
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

        // Fetch the context.
        val context = categoryHolder.categoryName.context

        // Make a spec view for every spec.
        specCategory.specs.forEach {
            // Make a view for a spec, set the values and add to the container.
            val view = makeSpecLayoutItemParent(context)
            // Decide what type of spec it is.
            if (it is StringSpec) {
                addStringSpecLayoutItem(it, view)
            } else {
                Log.e("Spec view", "Spec of type ${it.getType().name} is not supported for visualization.")
            }
            categoryHolder.specsContainer.addView(view)
        }
    }

    /**
     * Makes the layout in which the spec items get placed.
     *
     * @param context
     * @return
     */
    private fun makeSpecLayoutItemParent(context: Context): ViewGroup {
        val layout = RelativeLayout(context)
        // Params for width and height.
        layout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        return layout
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
        // Left text view.
        val textViewLeft = TextView(parent.context)
        textViewLeft.text = stringSpec.getDisplayName()
        val paramsLeft = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        paramsLeft.setMargins(dpToPx(20, ct), dpToPx(20, ct), dpToPx(20, ct), dpToPx(20, ct))
        paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_START)
        paramsLeft.addRule(RelativeLayout.CENTER_VERTICAL)
        textViewLeft.layoutParams = paramsLeft
        parent.addView(textViewLeft)
        // Right text view.
        val textViewRight = TextView(parent.context)
        textViewRight.text = stringSpec.getValue()
        val paramsRight = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        paramsRight.setMargins(dpToPx(20, ct), 0, dpToPx(20, ct), 0) // Left and right margin.
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_END) // Align right.
        paramsRight.addRule(RelativeLayout.CENTER_VERTICAL) // Center vertically.
        textViewRight.maxWidth =  screenWidth/ 2 // Max width is half of screen, needed to start new line.
        textViewRight.layoutParams = paramsRight // Set the layout parameters.
        textViewRight.maxLines = 2 // Max lines.
        textViewRight.textAlignment = TEXT_ALIGNMENT_VIEW_END // Align text right.
        parent.addView(textViewRight)
    }



    /**
     * Convert dp value to pixels.
     * Based on source: https://medium.com/@euryperez/android-pearls-set-size-to-a-view-in-dp-programatically-71d22eed7fc0
     *
     * @param dp The dp.
     * @param context
     * @return The pixel value.
     */
    fun dpToPx(dp: Int, context:Context): Int {
        val density = context.resources
            .displayMetrics
            .density
        return Math.round(dp.toFloat() * density)
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