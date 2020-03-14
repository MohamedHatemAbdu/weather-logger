package com.me.presentation.base.view.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.me.presentation.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigation()


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host)
        setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        Navigation.findNavController(this, R.id.nav_host).navigateUp()


}
