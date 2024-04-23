package br.com.roqls23.desafio.luizalabs.core.domain.usecase

import br.com.roqls23.desafio.luizalabs.core.domain.entity.StateEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.StateRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindStatesUseCase @Inject constructor(
    private val stateRepository: StateRepository
) {
    suspend operator fun invoke(): Flow<DataState<List<StateEntity>>> = flow {
        emit(DataState.Loading)

        val response = stateRepository.findAll()

        if (response.isSuccessful && response.body() != null) {
            emit(DataState.Success(data = response.body()!!))
        } else {
            emit(DataState.Error(Throwable("States not found")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(Throwable(e)))
    }
}