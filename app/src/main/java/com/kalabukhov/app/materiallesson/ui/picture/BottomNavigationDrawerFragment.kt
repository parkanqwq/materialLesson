package com.kalabukhov.app.materiallesson.ui.picture

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kalabukhov.app.materiallesson.R
import com.kalabukhov.app.materiallesson.ui.companion_object.blackTheme
import com.kalabukhov.app.materiallesson.ui.companion_object.blueTheme
import com.kalabukhov.app.materiallesson.ui.companion_object.orangeTheme
import kotlinx.android.synthetic.main.bottom_navigation_layout.*

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_navigation_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        navigation_view.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.blueTheme -> {
                    val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
                    editor?.putString(dataKeyTheme, blueTheme)
                    editor?.apply()
                    activity?.recreate()
                }
                R.id.blackTheme -> {
                    val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
                    editor?.putString(dataKeyTheme, blackTheme)
                    editor?.apply()
                    activity?.recreate()
                }
                R.id.orangeTheme -> {
                    val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
                    editor?.putString(dataKeyTheme, orangeTheme)
                    editor?.apply()
                    activity?.recreate()
                }
            }
            true
        }
    }

    companion object {
        const val dataKeyTheme = "dataKeyTheme"
    }
}
