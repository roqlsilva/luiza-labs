package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository

interface BaseRepository<T> {
    suspend fun create(entity: T): Long
    suspend fun update(entity: T)
    suspend fun findOne(id: Long): T?
    suspend fun findAll(): List<T>
    suspend fun delete(id: Long)
}