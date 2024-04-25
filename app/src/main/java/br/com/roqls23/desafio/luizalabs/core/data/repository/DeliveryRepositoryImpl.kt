package br.com.roqls23.desafio.luizalabs.core.data.repository

import br.com.roqls23.desafio.luizalabs.core.data.database.dao.DeliveryDao
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository

class DeliveryRepositoryImpl(
    private val dao: DeliveryDao
) : DeliveryRepository {
    override suspend fun create(entity: DeliveryEntity): Long =
        dao.create(entity)

    override suspend fun update(entity: DeliveryEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: Long): DeliveryEntity? =
        dao.findOne(id)

    override suspend fun findAll(): List<DeliveryEntity> =
        dao.findAll()

    override suspend fun delete(id: Long) {
        this.findOne(id)?.let { entity ->
            dao.delete(entity)
        }
    }
}