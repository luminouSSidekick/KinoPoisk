package com.example.films.activitys

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.films.R
import com.example.films.databinding.ActivityMainBinding
import com.example.films.extensions.repeatOnViewLifecycleStart
import com.example.films.ui.fragments.films.FilmsFragmentDirections
import com.example.films.viewModel.NavigationActionViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val navigationActionViewModel by viewModels<NavigationActionViewModel>()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.isTitleCentered = true
        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        repeatOnViewLifecycleStart {
            launch { collectNavigationAction() }
        }
    }

    private suspend fun collectNavigationAction() {
        navigationActionViewModel.screenEventFlow.collect { action ->
            openScreenByNavigationAction(action)
        }
    }

    private fun openScreenByNavigationAction(
        action: NavigationAction?
    ) {
        if (action == null) return

        when (action) {
            is NavigationAction.NavigationToFilmInfo ->
                handleNavigateToFilmInfo(action)
        }
    }

    private fun handleNavigateToFilmInfo(
        action: NavigationAction.NavigationToFilmInfo
    ) {
        val direction = FilmsFragmentDirections.actionMainFragmentToFilmInfoFragment(action.filmInfo)
        getNavigationController().navigate(direction)
    }

    private fun getNavigationController() =
        findNavController(R.id.nav_host_fragment_content_main)

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp(appBarConfiguration)
        return super.onSupportNavigateUp()
    }

}