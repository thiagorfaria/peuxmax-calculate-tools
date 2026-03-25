package com.peumax.calculatetools.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule
// Use cases are injected directly via @Inject constructor — no manual bindings needed yet.
