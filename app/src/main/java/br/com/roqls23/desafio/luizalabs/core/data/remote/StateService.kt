package br.com.roqls23.desafio.luizalabs.core.data.remote

import br.com.roqls23.desafio.luizalabs.core.domain.entity.StateEntity
import retrofit2.Response
import retrofit2.http.GET

interface StateService {

    @GET("localidades/estados")
    suspend fun findAllUFs(
    ): Response<List<StateEntity>>
}