package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository

import br.com.roqls23.desafio.luizalabs.core.domain.entity.StateEntity
import retrofit2.Response

interface StateRepository {
    suspend fun findAll(): Response<List<StateEntity>>
}