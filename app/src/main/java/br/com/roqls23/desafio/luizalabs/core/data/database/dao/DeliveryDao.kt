package br.com.roqls23.desafio.luizalabs.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity

@Dao
interface DeliveryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(entity: DeliveryEntity): Long
}