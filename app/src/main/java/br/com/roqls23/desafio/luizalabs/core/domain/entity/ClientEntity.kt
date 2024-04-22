package br.com.roqls23.desafio.luizalabs.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tb_clients")
data class ClientEntity(
    @PrimaryKey(autoGenerate = false) val id: Long = 0L,
    @ColumnInfo val clientName: String,
    @ColumnInfo val clientCpf: String,
)
