package br.com.roqls23.desafio.luizalabs.core.domain.dto

data class CreateDeliveryForm(
    var deliveryId: String = "",
    var packagesCount: Int = 0,
    var dueDate: String = "",
    var clientName: String = "",
    var clientCPF: String = "",
    var postalCode: String = "",
    var uf: String = "",
    var city: String = "",
    var district: String = "",
    var street: String = "",
    var number: String = "",
    var complement: String = "",
)
