package ru.krirll.dogsgram.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.krirll.dogsgram.domain.model.Dog

interface Repository {

    suspend fun getDogs(): Flow<List<Dog>>
}