package com.example.topmusicalbumsapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Album
import com.example.domain.repository.Resource
import com.example.topmusicalbumsapp.R
import com.example.topmusicalbumsapp.viewmodel.AlbumViewModel

@Composable
fun AlbumListScreen(viewModel: AlbumViewModel = hiltViewModel(), navController: NavController) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    when (state) {
        is Resource.Loading -> AlbumLoadingScreen()
        is Resource.Error ->
            Toast.makeText(
                context,
                stringResource(id = R.string.error_title_text) + (state as Resource.Error).message,
                Toast.LENGTH_SHORT
            ).show()

        is Resource.Success -> {
            val albums = (state as Resource.Success<List<Album>>).data
            if (albums.isEmpty()) {
                AlbumErrorScreen(
                    stringResource(R.string.no_data_title_text),
                    stringResource(R.string.no_data_description_text),
                    retryOnClick = { viewModel.fetchAlbums() })
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(albums) { album ->
                        AlbumItem(album) {
                            navController.navigate("album_detail/${album.id}")
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(album.artworkUrl100),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = album.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
            Text(text = album.artistName, style = MaterialTheme.typography.bodySmall)
        }
    }
}