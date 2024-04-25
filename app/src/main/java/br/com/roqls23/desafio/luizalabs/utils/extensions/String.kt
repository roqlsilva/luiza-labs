package br.com.roqls23.desafio.luizalabs.utils.extensions

import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.enums.TextInputMask

fun String.masked(mask: TextInputMask): String {
    var out = mask.mask
    this.forEach { letter ->
        out = out.replaceFirst('#', letter)
    }
    return out
}