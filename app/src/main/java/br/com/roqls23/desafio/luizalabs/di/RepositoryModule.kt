package br.com.roqls23.desafio.luizalabs.di

import br.com.roqls23.desafio.luizalabs.core.data.database.dao.DeliveryDao
import br.com.roqls23.desafio.luizalabs.core.data.remote.DistrictService
import br.com.roqls23.desafio.luizalabs.core.data.remote.StateService
import br.com.roqls23.desafio.luizalabs.core.data.repository.DeliveryRepositoryImpl
import br.com.roqls23.desafio.luizalabs.core.data.repository.DistrictRepositoryImpl
import br.com.roqls23.desafio.luizalabs.core.data.repository.StateRepositoryImpl
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DeliveryRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.DistrictRepository
import br.com.roqls23.desafio.luizalabs.core.domain.interfaces.repository.StateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideDeliveryRepository(dao: DeliveryDao): DeliveryRepository =
        DeliveryRepositoryImpl(dao = dao)

    @Provides
    @Singleton
    fun provideDistrictRepository(service: DistrictService): DistrictRepository =
        DistrictRepositoryImpl(service = service)

    @Provides
    @Singleton
    fun provideStateRepository(service: StateService): StateRepository =
        StateRepositoryImpl(service = service)
}