package com.kalabukhov.app.materiallesson.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kalabukhov.app.materiallesson.R
import com.kalabukhov.app.materiallesson.ui.companion_object.blackTheme
import com.kalabukhov.app.materiallesson.ui.companion_object.blueTheme
import com.kalabukhov.app.materiallesson.ui.companion_object.orangeTheme
import com.kalabukhov.app.materiallesson.ui.picture.BottomNavigationDrawerFragment
import com.kalabukhov.app.materiallesson.ui.picture.BottomNavigationDrawerFragment.Companion.dataKeyTheme
import com.kalabukhov.app.materiallesson.ui.picture.PictureOfTheDayFragment

var themesForApp: String = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        lookingThemes()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    private fun lookingThemes() {
        themesForApp = getPreferences(Context.MODE_PRIVATE)
            ?.getString(dataKeyTheme, "")
            ?: ""
        if (themesForApp == blueTheme) {
            setTheme(R.style.blueTheme)
        }
        if (themesForApp == blackTheme) {
            setTheme(R.style.blackTheme)
        }
        if (themesForApp == orangeTheme) {
            setTheme(R.style.orangeTheme)
        }
    }
}