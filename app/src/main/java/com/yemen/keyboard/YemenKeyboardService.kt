package com.yemen.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.widget.Button
import android.widget.LinearLayout

class YemenKeyboardService : InputMethodService() {

    override fun onCreateInputView(): View {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val row = LinearLayout(this)

        val letters = listOf(
            "ض", "ص", "ث", "ق", "ف", "غ", "ع", "ه", "خ", "ح", "ج"
        )

        for (letter in letters) {
            val button = Button(this)
            button.text = letter

            button.setOnClickListener {
                currentInputConnection?.commitText(letter, 1)
            }

            row.addView(button)
        }

        layout.addView(row)

        return layout
    }
}
