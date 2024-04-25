package br.com.roqls23.desafio.luizalabs.core.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.roqls23.desafio.luizalabs.core.domain.dto.CreateDeliveryForm

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
    @ColumnInfo val postalCode: String,
    @ColumnInfo val state: String,
    @ColumnInfo val city: String,
    @ColumnInfo val district: String,
    @ColumnInfo val street: String,
    @ColumnInfo val number: String,
    @ColumnInfo val complement: String? = null,
) {
    fun toCreateDeliveryForm(): CreateDeliveryForm =
        CreateDeliveryForm(
            deliveryId = this.deliveryId,
            packagesCount = this.packagesCount,
            dueDate = this.dueDate,
            clientName = this.clientName,
            clientCPF = this.clientCpf,
            postalCode = this.postalCode,
            uf = this.state,
            city = this.city,
            district = this.district,
            street = this.street,
            number = this.number,
            complement = this.complement ?: ""
        )
}