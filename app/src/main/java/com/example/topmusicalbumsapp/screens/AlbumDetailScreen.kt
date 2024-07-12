package com.example.topmusicalbumsapp.screens


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.topmusicalbumsapp.viewmodel.AlbumViewModel
import kotlinx.coroutines.flow.map

@Composable
fun AlbumDetailScreen(albumId: String, viewModel: AlbumViewModel) {
    val album by viewModel.albums.map { it.firstOrNull { album -> album.id == albumId } }
        .collectAsState(initial = null)

    album?.let { album ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = album.artworkUrl100),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Text(album.name, style = MaterialTheme.typography.headlineSmall)
            Text(album.artistName, style = MaterialTheme.typography.headlineMedium)
            Text("Genre: ${album.genre}", style = MaterialTheme.typography.bodySmall)
            Text("Release Date: ${album.releaseDate}", style = MaterialTheme.typography.bodySmall)
            Text(album.copyright, style = MaterialTheme.typography.labelSmall)

            Spacer(modifier = Modifier.height(16.dp))
            val context = LocalContext.current

            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(album.url))
                context.startActivity(intent)
            }) {
                Text("Open in iTunes")
            }
        }
    }
}