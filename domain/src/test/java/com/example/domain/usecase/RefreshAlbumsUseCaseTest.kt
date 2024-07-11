package com.example.domain.usecase

import com.example.domain.repository.AlbumRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RefreshAlbumsUseCaseTest {
    private val repo: AlbumRepository = mockk()
    private val useCase: RefreshAlbumsUseCase = RefreshAlbumsUseCase(repo)

    @Test
    fun `call repo When call usa case`() = runTest {
        // Given
        coEvery { repo.refreshAlbums() } returns mockk()
        // When
        useCase.invoke()
        // Then
        coVerify { repo.refreshAlbums() }
    }
}