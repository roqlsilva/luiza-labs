package br.com.roqls23.desafio.luizalabs.core.data.repository

import br.com.roqls23.desafio.luizalabs.core.data.remote.StateService
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.StateRepository

class StateRepositoryImpl(
    private val service: StateService
) : StateRepository {
    override suspend fun findAll() = service.findAllUFs()
}