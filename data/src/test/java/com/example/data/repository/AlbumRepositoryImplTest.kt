package com.example.data.repository

import com.example.data.local.realm.AlbumRealmModel
import com.example.data.remote.ApiService
import com.example.data.remote.model.AlbumResponse
import com.example.data.remote.model.Feed
import com.example.data.remote.model.Genre
import com.example.domain.model.Album
import com.example.domain.repository.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AlbumRepositoryImplTest {

    private lateinit var repository: AlbumRepositoryImpl
    private val realm = mockk<Realm>(relaxed = true)
    private val apiService = mockk<ApiService>()

    @Before
    fun setUp() {
        repository = AlbumRepositoryImpl(realm, apiService)
    }

    @Test
    fun `getAlbums emits loading and success from API`() = runBlocking {
        // Mock API response
        val albumDto = mockk<com.example.data.remote.model.Album>(relaxed = true)
        every { albumDto.id } returns "1"
        every { albumDto.name } returns "Album 1"
        every { albumDto.artistName } returns "Artist 1"
        every { albumDto.artworkUrl100 } returns "artworkUrl100"
        every { albumDto.releaseDate } returns "Date"
        every { albumDto.genres } returns listOf(Genre("Genre"))
        every { albumDto.url } returns "url"

        val feed = mockk<Feed>()
        every { feed.results } returns listOf(albumDto)
        every { feed.copyright } returns "Copyright"

        val response = mockk<AlbumResponse>()
        every { response.feed } returns feed
        coEvery { apiService.getTopAlbums() } returns response

        // Mock Realm
        every { realm.writeBlocking(any<MutableRealm.() -> Unit>()) } answers {
            val writer = firstArg<MutableRealm.() -> Unit>()
            writer.invoke(mockk())
        }

        val mockRealmResults = mockk<RealmResults<AlbumRealmModel>>(relaxed = true)
        every { mockRealmResults.iterator() } returns emptyList<AlbumRealmModel>().iterator()
        every { realm.query<AlbumRealmModel>().find() } returns mockRealmResults

        val flow = repository.getAlbums()
        val result = mutableListOf<Resource<List<Album>>>()
        flow.toList(result)

        assertEquals(2, result.size)
        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Success)

        coVerify(exactly = 1) { apiService.getTopAlbums() }
        verify(exactly = 1) { realm.writeBlocking(any<MutableRealm.() -> Unit>()) }
        verify(exactly = 1) { realm.query<AlbumRealmModel>().find() }
    }

    @Test
    fun `getAlbums emits success from cache on error`() = runBlocking {
        // Mock API error
        coEvery { apiService.getTopAlbums() } throws Exception("API error")

        // Mock Realm
        val albumRealmModel = mockk<AlbumRealmModel>(relaxed = true)
        val mockRealmResults = mockk<RealmResults<AlbumRealmModel>>(relaxed = true)
        every { mockRealmResults.iterator() } returns listOf(albumRealmModel).iterator()
        every { realm.query<AlbumRealmModel>().find() } returns mockRealmResults

        val flow = repository.getAlbums()
        val result = mutableListOf<Resource<List<Album>>>()
        flow.toList(result)

        assertEquals(2, result.size)
        assert(result[0] is Resource.Loading)
        assert(result[1] is Resource.Success)

        coVerify(exactly = 1) { apiService.getTopAlbums() }
        verify(exactly = 1) { realm.query<AlbumRealmModel>().find() }
    }
}
