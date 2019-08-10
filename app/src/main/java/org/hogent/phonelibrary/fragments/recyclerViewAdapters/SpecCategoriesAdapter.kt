package org.hogent.phonelibrary.fragments.recyclerViewAdapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.device_category_list_row.view.*
import org.hogent.phonelibrary.R
import org.hogent.phonelibrary.domain.models.SpecCategory


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

        // Make a view for a spec, set the values and add to the container.
        val view = makeSpecLayoutItemParent(context)
        addStringSpecLayoutItem(view)

        categoryHolder.specsContainer.addView(view)
    }

    private fun makeSpecLayoutItemParent(context: Context): ViewGroup{
        val layout = RelativeLayout(context)
        // Params for width and height.
        layout.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        return layout
    }

    private fun addStringSpecLayoutItem(parent:ViewGroup) {
        // Left text view.
        val textViewLeft = TextView(parent.context)
        textViewLeft.text = "SPEC NAME"
        val paramsLeft = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        paramsLeft.setMargins(20,20,20,20)
        paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_START)
        parent.addView(textViewLeft)
        textViewLeft.layoutParams = paramsLeft
        // Right text view.
        val textViewRight = TextView(parent.context)
        textViewRight.text = "SPEC DATA"
        val paramsRight = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        paramsRight.setMargins(20,20,20,20)
        paramsRight.addRule(RelativeLayout.ALIGN_PARENT_END)
        parent.addView(textViewRight)
        textViewRight.layoutParams = paramsRight
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