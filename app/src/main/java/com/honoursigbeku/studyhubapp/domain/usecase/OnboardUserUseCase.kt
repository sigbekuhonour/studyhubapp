package com.honoursigbeku.studyhubapp.domain.usecase

import com.honoursigbeku.studyhubapp.domain.repository.NoteFolderRepository

class OnboardUserUseCase(private val folderRepository: NoteFolderRepository) {
    private val defaultFolders = listOf("Quick Notes", "Shared Notes", "Deleted Notes")
    suspend operator fun invoke(userId: String): Result<Unit> {
        return try {
            defaultFolders.forEach { folderName ->
                folderRepository.addFolder(name = folderName, userId = userId)
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}