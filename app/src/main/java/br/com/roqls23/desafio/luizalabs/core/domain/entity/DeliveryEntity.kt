package br.com.roqls23.desafio.luizalabs.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "tb_deliveries",
)
data class DeliveryEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0L,
    @ColumnInfo val packagesCount: Int,
    @ColumnInfo val dueData: String,
//    @ColumnInfo val client: ClientEntity,
//    @ColumnInfo val address: AddressEntity,
)