package com.emikhalets.convertapp.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.emikhalets.convertapp.presentation.screens.CurrenciesScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, AppRoute.Currencies) {
        composable(AppRoute.Currencies) {
            CurrenciesScreen(
                viewModel = hiltViewModel(),
            )
        }
    }
}
