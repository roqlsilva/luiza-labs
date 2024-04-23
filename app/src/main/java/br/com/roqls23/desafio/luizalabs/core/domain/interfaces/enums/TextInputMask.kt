package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.enums

enum class TextInputMask(val mask: String) {
    CPF("###.###.###-##"),
    CEP("#####-###");

    val inputLength: Int get() = this.mask.count { it == '#' }
}