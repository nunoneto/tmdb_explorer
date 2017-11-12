package pt.nunoneto.tmdbexplorer.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class Utils {

    companion object {

        fun hideKeyboard(view: View?) {
            if (view != null) {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }
}