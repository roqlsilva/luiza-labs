package br.com.roqls23.desafio.luizalabs.core.data.repository

import br.com.roqls23.desafio.luizalabs.core.data.remote.DistrictService
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DistrictEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DistrictRepository

class DistrictRepositoryImpl(
    private val service: DistrictService
) : DistrictRepository {
    override suspend fun findAll(uf: String) = service.findAllByUF(uf)
}