package com.jerry.moneytracker.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jerry.moneytracker.R
import com.jerry.moneytracker.components.BottomBar
import com.jerry.moneytracker.components.BottomBarItem
import com.jerry.moneytracker.core.ui.navigation.MainRoute
import com.jerry.moneytracker.feature.home.ui.screen.HomeRoute
import com.jerry.moneytracker.feature.home.ui.screen.HomeScreen
import com.jerry.moneytracker.feature.home.ui.viewmodel.HomeScreenViewModel
import com.jerry.moneytracker.feature.setting.ui.screen.SettingRoute
import com.jerry.moneytracker.feature.setting.ui.viewmodel.SettingViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavHost(
    mainNavController: NavHostController = rememberNavController(),
    settingViewModel: SettingViewModel,
) {

    val currentSelectedScreen by mainNavController.currentScreenAsState()
    val setting = settingViewModel.settingState.collectAsStateWithLifecycle().value
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBar(
                currentSelectedScreen = currentSelectedScreen,
                items = listOf(
                    BottomBarItem(
                        route = MainRoute.HomeScreen.route,
                        title = stringResource(id = R.string.app_main_nav_home),
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                        onClick = {
                            mainNavController.navigate(MainRoute.HomeScreen.route)
                        }
                    ),
                    BottomBarItem(
                        route = MainRoute.TransactionScreen.route,
                        title = stringResource(id = R.string.app_main_nav_transaction),
                        selectedIcon = Icons.Filled.Analytics,
                        unselectedIcon = Icons.Outlined.Analytics,
                        onClick = {
                            mainNavController.navigate(MainRoute.TransactionScreen.route)
                        }
                    ),
                    BottomBarItem(
                        route = MainRoute.SettingScreen.route,
                        title = stringResource(id = R.string.app_main_nav_setting),
                        selectedIcon = Icons.Filled.Settings,
                        unselectedIcon = Icons.Outlined.Settings,
                        onClick = {
                            mainNavController.navigate(MainRoute.SettingScreen.route)
                        }
                    )
                )
            )
        }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = mainNavController,
            startDestination = MainRoute.HomeScreen.route
        ){
            composable(MainRoute.HomeScreen.route) {
                HomeRoute(
                    mainNavController = mainNavController,
                    setting = setting,
                    homeScreenViewModel = homeScreenViewModel,
                )
            }

            composable(MainRoute.TransactionScreen.route) {
//                TransactionScreen(
//                    setting = setting,
//                )
                Text ("this is TransactionScreen")
            }

            composable(MainRoute.SettingScreen.route) {
                SettingRoute(
                    viewModel = settingViewModel
                )
            }
        }
    }
}

@Stable
@Composable
private fun NavController.currentScreenAsState(): State<MainRoute> {
    val selectedItem = remember { mutableStateOf<MainRoute>(MainRoute.HomeScreen) }
    DisposableEffect(key1 = this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route ==MainRoute.HomeScreen.route } -> {
                    selectedItem.value = MainRoute.HomeScreen
                }
                destination.hierarchy.any { it.route == MainRoute.TransactionScreen.route } -> {
                    selectedItem.value = MainRoute.TransactionScreen
                }
                destination.hierarchy.any { it.route == MainRoute.SettingScreen.route } -> {
                    selectedItem.value = MainRoute.SettingScreen
                }
            }

        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}

@Stable
@Composable
private fun NavController.currentRouteAsState(): State<String?> {
    val selectedItem = remember { mutableStateOf<String?>(null) }
    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedItem.value = destination.route
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}
