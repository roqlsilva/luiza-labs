package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state

import retrofit2.HttpException

interface UiState {
    object Empty : UiState
    object Loading : UiState
    data class Error(val throwable: Throwable) : UiState {
        val statusCode: Int
            get() = if (this.throwable is HttpException) {
                this.throwable.code()
            } else {
                -1
            }

        val errorMessage: String
            get() = this.throwable.message ?: "Unexpected error."
    }
}