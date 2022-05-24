package com.althurdinok.fromandwith.di.module

import com.althurdinok.fromandwith.data.repository.onborading.OnBoardingRepository
import com.althurdinok.fromandwith.data.repository.onborading.impl.OnBoardingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideOnBoardingRepository(): OnBoardingRepository = OnBoardingRepositoryImpl()
}