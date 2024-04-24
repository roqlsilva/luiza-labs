package br.com.roqls23.desafio.luizalabs.core.domain.usecase

import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(data: CreateDeliveryForm): Flow<DataState<DeliveryEntity>> = flow {
        emit(DataState.Loading)

        val delivery = DeliveryEntity(
            deliveryId = data.deliveryId,
            packagesCount = data.packagesCount,
            dueDate = data.dueDate,
            clientName = data.clientName,
            clientCpf = data.clientCPF
        )

        val id = deliveryRepository.create(delivery)
        deliveryRepository.findOne(id)?.let {
            emit(DataState.Success(data = it))
        } ?: run {
            emit(DataState.Error(Throwable("Delivery not found")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(Throwable(e)))
    }
}