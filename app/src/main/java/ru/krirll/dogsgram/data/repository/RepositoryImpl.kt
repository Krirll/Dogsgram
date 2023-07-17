package ru.krirll.dogsgram.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.krirll.dogsgram.data.network.Api
import ru.krirll.dogsgram.domain.model.Dog
import ru.krirll.dogsgram.domain.repository.Repository

class RepositoryImpl(
    private val apiService: Api
): Repository {

    override suspend fun getDogs(): Flow<List<Dog>> = flow {
        emit(
            mutableListOf<Dog>().apply {
                apiService.getDogs().urlList.forEach {
                    add(Dog(it))
                }
            }
        )
    }
}