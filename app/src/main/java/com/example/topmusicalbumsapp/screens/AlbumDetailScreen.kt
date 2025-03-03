package com.example.topmusicalbumsapp.screens


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Album
import com.example.domain.repository.Resource
import com.example.topmusicalbumsapp.R
import com.example.topmusicalbumsapp.viewmodel.AlbumViewModel

@Composable
fun AlbumDetailScreen(albumId: String?, viewModel: AlbumViewModel) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is Resource.Loading -> AlbumLoadingScreen()
        is Resource.Success -> {
            val album = (state as Resource.Success<List<Album>>).data.find { it.id == albumId }
            album?.let {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(album.artworkUrl100),
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = album.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(text = album.artistName, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Genre: ${album.genre}", style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = "Release Date: ${album.releaseDate}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = album.copyright,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    val context = LocalContext.current
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(album.url))
                        context.startActivity(intent)
                    }) {
                        Text(text = stringResource(id = R.string.itunes_button_text))
                    }
                }
            }
        }
    }
}