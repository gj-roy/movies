package org.michaelbel.movies.common.localization.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.common.localization.impl.LocaleControllerImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface LocaleModule {

    @Binds
    @Singleton
    fun provideLocaleController(controller: LocaleControllerImpl): LocaleController
}