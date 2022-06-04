package com.f3401pal.playground.jg.di

import android.content.Context
import com.f3401pal.playground.jg.db.AppDatabase
import com.f3401pal.playground.jg.ext.CoroutineDispatcherProvider
import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.newInstance(context)
    }

    @Provides
    @Singleton
    fun provideCoroutineDispatcherProvider() = object : CoroutineDispatcherProvider {
        override val background = Dispatchers.Default
        override val io = Dispatchers.IO
        override val ui = Dispatchers.Main
    }
}