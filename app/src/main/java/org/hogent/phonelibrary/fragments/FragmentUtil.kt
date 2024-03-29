package org.hogent.phonelibrary.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.EditText
import androidx.appcompat.widget.AppCompatDrawableManager
import org.hogent.phonelibrary.R

/**
 * Utility class which can be used on controls/views of fragments.
 *
 */
class FragmentUtil {
    companion object {
        /**
         * Help function for observing the text of an EditText by using a lambda expression.
         * Source: https://stackoverflow.com/questions/40569436/kotlin-addtextchangelistener-lambda
         *
         * @param afterTextChanged
         */
        fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(editable: Editable?) {
                    afterTextChanged.invoke(editable.toString())
                }
            })
        }

        /**
         * Animation for making a view shake.
         * Source: https://stackoverflow.com/questions/15401658/vibration-of-edittext-in-android
         *
         * @return
         */
        fun shakeView(): TranslateAnimation {
            val shake = TranslateAnimation(0f, 10f, 0f, 0f)
            shake.duration = 500
            shake.interpolator = CycleInterpolator(5f)
            return shake
        }

        /**
         * Help function to show or hide a clear button on the edit text.
         * Based on source: https://stackoverflow.com/questions/6355096/how-to-create-edittext-with-crossx-button-at-end-of-it
         * Updated the calculation from 'event.rawX >= (this.right - this.compoundPaddingRight)' to '(this.width - this.compoundPaddingRight) < event.x'
         *
         */
        fun EditText.setupClearButtonWithAction() {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    // Show clear icon if edit text is not empty.
                    val icon = if (editable?.isNotBlank() == true) {
                        R.drawable.ic_clear
                    } else 0
                    // Set icon. If edit text was not empty, value is 0 and nothing is shown.
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            })

            setOnTouchListener(View.OnTouchListener { inputView, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    // Check if the area of the clear icon was selected.
                    if ((this.width - this.compoundPaddingRight) < event.x) {
                        this.setText("")
                        return@OnTouchListener true
                    }
                    val clearIcon = if ((inputView as EditText).text.isNotBlank()) R.drawable.ic_clear else 0
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
                }
                return@OnTouchListener false
            })
        }
    }
}