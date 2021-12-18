package com.test.randomusers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.test.randomusers.R
import com.test.randomusers.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initBinding()
    }

    private fun initBinding() {
        // Finding the Navigation Controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setting up the ActionBar with Navigation controller
        appBarConfiguration = AppBarConfiguration(setOf(R.id.user_fragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        // This helps to customise the toolbar i.e the back button
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.user_details_fragment) {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
            }
        }

    }

    /**
     * This enables click listener on the back button
     */
    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
}