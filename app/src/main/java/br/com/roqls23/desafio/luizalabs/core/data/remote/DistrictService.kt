package br.com.roqls23.desafio.luizalabs.core.data.remote

import br.com.roqls23.desafio.luizalabs.core.domain.entity.DistrictEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DistrictService {

    @GET("localidades/estados/{uf}/distritos")
    suspend fun findAllByUF(
        @Path("uf") uf: String
    ): Response<List<DistrictEntity>>
}