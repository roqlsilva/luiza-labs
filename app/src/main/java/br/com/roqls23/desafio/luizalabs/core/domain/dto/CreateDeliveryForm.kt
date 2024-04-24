package br.com.roqls23.desafio.luizalabs.core.domain.dto

data class CreateDeliveryForm(
    var deliveryId: String = "",
    var packagesCount: Int = 0,
    var dueDate: String = "",
    var clientName: String = "",
    var clientCPF: String = "",
)
