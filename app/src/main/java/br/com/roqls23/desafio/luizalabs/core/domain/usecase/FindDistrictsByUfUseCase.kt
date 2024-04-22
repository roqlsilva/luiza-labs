package br.com.roqls23.desafio.luizalabs.core.domain.usecase

import br.com.roqls23.desafio.luizalabs.core.domain.entity.DistrictEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DistrictRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindDistrictsByUfUseCase @Inject constructor(
    private val districtRepository: DistrictRepository
) {
    suspend operator fun invoke(uf: String): Flow<DataState<List<DistrictEntity>>> = flow {
        emit(DataState.Loading)

        val response = districtRepository.findAll(uf)

        if (response.isSuccessful && response.body() != null) {
            emit(DataState.Success(data = response.body()!!))
        } else {
            emit(DataState.Error(Throwable("Districts not found")))
        }
    }.catch { e ->
        e.printStackTrace()
        emit(DataState.Error(Throwable(e)))
    }
}