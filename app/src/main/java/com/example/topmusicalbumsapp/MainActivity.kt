package com.example.topmusicalbumsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.topmusicalbumsapp.screens.AppNavigation
import com.example.topmusicalbumsapp.ui.theme.TopMusicAlbumsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TopMusicAlbumsAppTheme {
                AppNavigation()
            }
        }
    }
}