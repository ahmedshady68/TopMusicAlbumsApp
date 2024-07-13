package com.example.topmusicalbumsapp.screens


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.topmusicalbumsapp.viewmodel.AlbumViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val albumViewModel: AlbumViewModel = hiltViewModel()

    NavHost(navController, startDestination = "album_list") {
        composable("album_list") {
            AlbumListScreen(navController = navController)
        }
        composable("album_detail/{albumId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId") ?: return@composable
            AlbumDetailScreen(albumId, albumViewModel)
        }
    }
}