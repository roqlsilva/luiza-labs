package br.com.roqls23.desafio.luizalabs.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tb_addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0L,
    @ColumnInfo val city: String,
    @ColumnInfo val postalCode: String,
    @ColumnInfo val district: String,
    @ColumnInfo val street: String,
    @ColumnInfo val number: String,
    @ColumnInfo val complement: String,
)
