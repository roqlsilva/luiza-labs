package br.com.roqls23.desafio.luizalabs.core.domain.usecase

import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Long): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            deliveryRepository.delete(id)
            emit(DataState.Success(data = true))
        } catch (e: Exception) {
            emit(DataState.Error(Throwable(e)))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(Throwable(e)))
    }
}