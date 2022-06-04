package com.f3401pal.playground.jg.di

import com.f3401pal.playground.jg.repository.TransactionRepository
import com.f3401pal.playground.jg.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindTransactionRepository(imp: TransactionRepositoryImpl): TransactionRepository

}