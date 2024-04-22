package br.com.roqls23.desafio.luizalabs.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.roqls23.desafio.luizalabs.core.data.database.dao.DeliveryDao
import br.com.roqls23.desafio.luizalabs.core.domain.entity.AddressEntity
import br.com.roqls23.desafio.luizalabs.core.domain.entity.ClientEntity
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity

@Database(
    entities = [
        ClientEntity::class,
        DeliveryEntity::class,
        AddressEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
}