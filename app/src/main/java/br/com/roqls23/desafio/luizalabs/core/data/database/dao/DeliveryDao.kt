package br.com.roqls23.desafio.luizalabs.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.roqls23.desafio.luizalabs.core.domain.entity.DeliveryEntity

@Dao
interface DeliveryDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(entity: DeliveryEntity): Long

    @Update
    suspend fun update(entity: DeliveryEntity)

    @Query("SELECT * FROM tb_deliveries WHERE id = :id")
    suspend fun findOne(id: Long): DeliveryEntity?

    @Query("SELECT * FROM tb_deliveries WHERE deliveryId = :deliveryId")
    suspend fun findByDeliveryId(deliveryId: String): DeliveryEntity?

    @Query("SELECT * FROM tb_deliveries ORDER BY deliveryId")
    suspend fun findAll(): List<DeliveryEntity>

    @Delete
    suspend fun delete(entity: DeliveryEntity)
}