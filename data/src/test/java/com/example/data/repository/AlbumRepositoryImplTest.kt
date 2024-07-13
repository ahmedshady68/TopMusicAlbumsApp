package com.example.data.repository
import com.example.data.local.AlbumDao
import com.example.data.local.AlbumEntity
import com.example.data.mapper.toDomain
import com.example.data.remote.ApiService
import com.example.data.remote.model.AlbumResponse
import com.example.data.remote.model.Feed
import com.example.data.remote.model.Genre
import com.example.data.remote.model.Album
import com.example.domain.repository.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AlbumRepositoryImplTest {

    private lateinit var albumDao: AlbumDao
    private lateinit var apiService: ApiService
    private lateinit var albumRepository: AlbumRepositoryImpl

    @Before
    fun setUp() {
        albumDao = mockk()
        apiService = mockk()
        albumRepository = AlbumRepositoryImpl(albumDao, apiService)
    }

    @Test
    fun `getAlbums returns success when api call is successful`() = runBlocking {
        // Arrange
        val mockAlbumsDto = listOf(
            Album(
                id = "1",
                name = "Album1",
                artistName = "Artist1",
                artworkUrl100 = "url1",
                releaseDate = "2021-01-01",
                genres = listOf(Genre(name = "Genre1")),
                url = "url1"
            )
        )
        val mockFeed = Feed(results = mockAlbumsDto, copyright = "Copyright")
        val mockApiResponse = AlbumResponse(feed = mockFeed)
        val mockAlbumEntities = mockAlbumsDto.map {
            AlbumEntity(
                id = it.id ?: "N/A",
                name = it.name ?: "N/A",
                artistName = it.artistName ?: "N/A",
                artworkUrl100 = it.artworkUrl100 ?: "N/A",
                releaseDate = it.releaseDate,
                copyright = mockFeed.copyright,
                genre = it.genres.firstOrNull()?.name,
                url = it.url ?: "N/A"
            )
        }
        val mockDomainAlbums = mockAlbumEntities.map { it.toDomain() }

        coEvery { apiService.getTopAlbums() } returns mockApiResponse
        coEvery { albumDao.insertAll(mockAlbumEntities) } returns Unit
        coEvery { albumDao.getAllAlbums() } returns flowOf(mockAlbumEntities)

        // Act
        val flow = albumRepository.getAlbums()

        // Assert
        val results = flow.toList()
        assert(results.first() is Resource.Loading)
        assert(results[1] is Resource.Success)
        assertEquals((results[1] as Resource.Success).data, mockDomainAlbums)

        coVerify(exactly = 1) { apiService.getTopAlbums() }
        coVerify(exactly = 1) { albumDao.insertAll(mockAlbumEntities) }
        coVerify(exactly = 1) { albumDao.getAllAlbums() }
    }

    @Test
    fun `getAlbums returns error when api call fails`() = runBlocking {
        // Arrange
        val exception = Exception("Network error")
        val mockAlbumEntities = listOf(
            AlbumEntity(
                id = "1",
                name = "Album1",
                artistName = "Artist1",
                artworkUrl100 = "url1",
                releaseDate = "2021-01-01",
                copyright = "Copyright",
                genre = "Genre1",
                url = "url1"
            )
        )
        val mockDomainAlbums = mockAlbumEntities.map { it.toDomain() }

        coEvery { apiService.getTopAlbums() } throws exception
        coEvery { albumDao.getAllAlbums() } returns flowOf(mockAlbumEntities)

        // Act
        val flow = albumRepository.getAlbums()

        // Assert
        val results = flow.toList()
        assert(results.first() is Resource.Loading)
        assert(results[1] is Resource.Error)
        assertEquals((results[1] as Resource.Error).message, "Network error")
        assert(results[2] is Resource.Success)
        assertEquals((results[2] as Resource.Success).data, mockDomainAlbums)

        coVerify(exactly = 1) { apiService.getTopAlbums() }
        coVerify(exactly = 1) { albumDao.getAllAlbums() }
    }
}
