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
         * Animation for making a view grow and then scale back.
         *
         * @return
         */
        fun growView(): ScaleAnimation {
            val grow =
                ScaleAnimation(1f, 1.3f, 1f, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            grow.duration = 300
            grow.interpolator = CycleInterpolator(2f)
            return grow
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
                    if (editable?.isNotBlank() == true) {
                        val icon = AppCompatDrawableManager.get().getDrawable(context, R.drawable.ic_clear)
                        setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            })

            setOnTouchListener(View.OnTouchListener { inputView, event ->
                if (event.action == MotionEvent.ACTION_UP) {
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