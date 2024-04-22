package br.com.roqls23.desafio.luizalabs.di

import android.content.Context
import androidx.room.Room
import br.com.roqls23.desafio.luizalabs.core.data.database.AppDatabase
import br.com.roqls23.desafio.luizalabs.core.data.database.dao.DeliveryDao
import br.com.roqls23.desafio.luizalabs.core.data.repository.DeliveryRepositoryImpl
import br.com.roqls23.desafio.luizalabs.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) =
        Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = Constants.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideDeliveryDao(database: AppDatabase) = database.deliveryDao()
}