package com.example.navigations

sealed class NavigationFlow {
    object MainFrag : NavigationFlow()
    object AuthFrag : NavigationFlow()
}