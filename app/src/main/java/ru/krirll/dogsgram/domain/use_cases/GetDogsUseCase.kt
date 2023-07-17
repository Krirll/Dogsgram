package ru.krirll.dogsgram.domain.use_cases

import ru.krirll.dogsgram.domain.repository.Repository

class GetDogsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke() = repository.getDogs()
}