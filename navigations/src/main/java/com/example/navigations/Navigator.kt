package com.example.navigations

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.MainFrag -> navController.navigate(MainNavGraphDirections.actionGlobalNavBase())
        NavigationFlow.AuthFrag -> navController.navigate(MainNavGraphDirections.actionGlobalNavAuth())
    }
}