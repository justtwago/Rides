package com.justtwago.rides.ui.vehiclelist.util

import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.onSearchClicked(onSearchClicked: (String) -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearchClicked(text.toString())
            true
        } else false
    }
}
