package br.com.roqls23.desafio.luizalabs.core.data.repository

import br.com.roqls23.desafio.luizalabs.core.data.database.dao.DeliveryDao
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository

class DeliveryRepositoryImpl(
    private val dao: DeliveryDao
) : DeliveryRepository {
    override fun create(entity: DeliveryEntity): Long =
        dao.create(entity)

    override fun update(entity: DeliveryEntity) {
        TODO("Not yet implemented")
    }

    override fun findOne(id: Long): DeliveryEntity? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<DeliveryEntity> {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}