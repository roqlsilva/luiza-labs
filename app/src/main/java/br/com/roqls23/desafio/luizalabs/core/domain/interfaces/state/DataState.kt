package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.state

import retrofit2.HttpException
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex.Empty

interface DataState<out R> {
    object Loading : DataState<Nothing>

    data class Success<out T>(val data: T) : DataState<T>

    data class Error(val throwable: Throwable) : DataState<Nothing> {
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