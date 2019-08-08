package org.hogent.phonelibrary.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText
import org.hogent.phonelibrary.R

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
            shake.interpolator = CycleInterpolator(7f)
            return shake
        }

        /**
         * Help function to show or hide a clear button on the edit text.
         * Source: https://stackoverflow.com/questions/6355096/how-to-create-edittext-with-crossx-button-at-end-of-it
         *
         */
        fun EditText.setupClearButtonWithAction() {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable?) {
                    val clearIcon = if (editable?.isNotBlank() == true) R.drawable.ic_clear else 0
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            })

            setOnTouchListener(View.OnTouchListener { inputView, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    val clearIcon = if ((inputView as EditText).text.isNotBlank()) R.drawable.ic_clear else 0
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)

                    if (event.x >= (this.right - this.compoundPaddingRight)) {
                        this.setText("")
                        return@OnTouchListener true
                    }
                }
                return@OnTouchListener false
            })
        }
    }
}