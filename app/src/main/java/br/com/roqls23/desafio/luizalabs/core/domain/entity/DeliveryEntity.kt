package br.com.roqls23.desafio.luizalabs.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tb_deliveries",
    indices = [
        Index("deliveryId", name = "idx_delivery_id", unique = true)
    ]
)
data class DeliveryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo val deliveryId: String,
    @ColumnInfo val packagesCount: Int,
    @ColumnInfo val dueDate: String,
    @ColumnInfo val clientName: String,
    @ColumnInfo val clientCpf: String,
//    @ColumnInfo val client: ClientEntity,
//    @ColumnInfo val address: AddressEntity,
)