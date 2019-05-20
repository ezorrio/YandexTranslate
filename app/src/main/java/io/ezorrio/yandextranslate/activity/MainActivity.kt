package io.ezorrio.yandextranslate.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.ezorrio.yandextranslate.R

class MainActivity : AppCompatActivity() {

    lateinit var mNavigationView: BottomNavigationView
    lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        mNavigationView = findViewById(R.id.bottom_navigation)
        mNavController = Navigation.findNavController(this, R.id.nav_host)
        NavigationUI.setupWithNavController(mNavigationView, mNavController)
    }

    fun setNavigationViewVisibility(visible: Boolean) {
        mNavigationView.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
