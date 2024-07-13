package com.example.topmusicalbumsapp.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.Album
import com.example.domain.repository.Resource
import com.example.domain.usecase.GetAlbumsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class AlbumViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var getAlbumsUseCase: GetAlbumsUseCase
    private lateinit var albumViewModel: AlbumViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getAlbumsUseCase = mockk()

        // Mock the use case before creating the view model
        coEvery { getAlbumsUseCase.invoke() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(emptyList<Album>()))
        }

        albumViewModel = AlbumViewModel(getAlbumsUseCase)
    }

    @Test
    fun `fetchAlbums updates state to success when use case returns data`() = runTest {
        // Arrange
        val mockAlbums = listOf(
            Album(
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
        coEvery { getAlbumsUseCase.invoke() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(mockAlbums))
        }

        // Act
        albumViewModel.fetchAlbums()

        // Assert
        val state = albumViewModel.state.value
        assert(state is Resource.Success)
        assertEquals((state as Resource.Success).data, mockAlbums)
    }

    @Test
    fun `fetchAlbums updates state to error when use case returns an error`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        coEvery { getAlbumsUseCase.invoke() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(errorMessage))
        }

        // Act
        albumViewModel.fetchAlbums()

        // Assert
        val state = albumViewModel.state.value
        assert(state is Resource.Error)
        assertEquals((state as Resource.Error).message, errorMessage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
