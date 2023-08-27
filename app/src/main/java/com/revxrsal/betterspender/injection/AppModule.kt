package com.revxrsal.betterspender.injection

import android.content.Context
import androidx.room.Room
import com.revxrsal.betterspender.data.SpenderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context,
            SpenderDatabase::class.java,
            "spender"
        )
        .fallbackToDestructiveMigration()
        .build()

}