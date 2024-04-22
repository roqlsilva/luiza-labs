package br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository

interface BaseRepository<T> {
    fun create(entity: T): Long
    fun update(entity: T)
    fun findOne(id: Long): T?
    fun findAll(): List<T>
    fun delete(id: Long)
}