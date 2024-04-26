package br.com.roqls23.desafio.luizalabs.core.domain.usecase

import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateDeliveryUseCase @Inject constructor(
    private val deliveryRepository: DeliveryRepository
) {
    suspend operator fun invoke(data: CreateDeliveryForm): Flow<DataState<DeliveryEntity>> = flow {
        emit(DataState.Loading)

        val entity = deliveryRepository.findByDeliveryId(data.deliveryId)?.copy(
            packagesCount = data.packagesCount,
            dueDate = data.dueDate,
            clientName = data.clientName,
            clientCpf = data.clientCPF,
            postalCode = data.postalCode,
            state = data.uf,
            city = data.city,
            district = data.district,
            street = data.street,
            number = data.number,
            complement = data.complement.takeIf { it.isNotEmpty() }
        )

        entity?.let {
            deliveryRepository.update(it)
            emit(DataState.Success(data = it))
        } ?: run {
            emit(DataState.Error(Throwable("Unexpected error.")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(Throwable(e)))
    }
}