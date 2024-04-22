package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository

import br.com.roqls23.desafio.luizalabs.core.domain.entity.DistrictEntity
import retrofit2.Response

interface DistrictRepository {
    suspend fun findAll(uf: String): Response<List<DistrictEntity>>
}