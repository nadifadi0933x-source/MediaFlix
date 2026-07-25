package com.mediaflix.app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.mediaflix.app.ui.screens.*
import com.mediaflix.app.viewmodel.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector, val selectedIcon: ImageVector) {
    data object Home : Screen("home", "محاله؆", Icons.Outlined.Home, Icons.Filled.Home)
    data object Movies : Screen("movies", "فیلم‌ها", Icons.Outlined.Movie, Icons.Filled.Movie)
    data object Series : Screen("series", "سریال‌ها", Icons.Outlined.Tv, Icons.Filled.Tv)
    data object Anime : Screen("anime", "انیمه‌ا", Icons.Outlined.PlayCircle, Icons.Filled.PlayCircle)
    data object Manga : Screen("manga", "مانگا", Icons.Outlined.Book, Icons.Filled.Book)
    data object Search : Screen("search", "جستجو", Icons.Outlined.Search, Icons.Filled.Search)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Movies,
    Screen.Series,
    Screen.Anime,
    Screen.Manga,
    Screen.Search
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaFlixApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    bottomNavItems.forEach { screen ->
                        val selected = currentRoute == screen.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon else screen.icon,
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title, style = MaterialTheme.typography.labelSmall) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Movies.route) { MoviesScreen(navController) }
            composable(Screen.Series.route) { SeriesScreen(navController) }
            composable(Screen.Anime.route) { AnimeScreen(navController) }
            composable(Screen.Manga.route) { MangaScreen(navController) }
            composable(Screen.Search.route) { SearchScreen(navController) }

            composable(
                route = "detail/{mediaId}",
                arguments = listOf(navArgument("mediaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mediaId = backStackEntry.arguments?.getString("mediaId") ?: return@composable
                DetailScreen(navController, mediaId)
            }

            composable(
                route = "player/{mediaId}",
                arguments = listOf(navArgument("mediaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mediaId = backStackEntry.arguments?.getString("mediaId") ?: return@composable
                PlayerScreen(navController, mediaId)
            }

            composable(
                route = "pdf/{mediaId}",
                arguments = listOf(navArgument("mediaId") { type = NavType.StringType })
            ) { backStackEntry ->
                val mediaId = backStackEntry.arguments?.getString("mediaId") ?: return@composable
                PdfReaderScreen(navController, mediaId)
            }
        }
    }
}