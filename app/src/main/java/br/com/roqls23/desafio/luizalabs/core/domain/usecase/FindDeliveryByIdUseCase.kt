package br.com.roqls23.desafio.luizalabs.core.domain.usecase

import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindDeliveryByIdUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(id: Long): Flow<DataState<DeliveryEntity>> = flow {
        emit(DataState.Loading)
        val deliveryEntity = deliveryRepository.findOne(id)

        if (deliveryEntity != null) {
            emit(DataState.Success(data = deliveryEntity))
        } else {
            emit(DataState.Error(Throwable("Not found")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(Throwable(e)))
    }
}