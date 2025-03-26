package com.ilshat.node.di.modules

import android.content.Context
import androidx.room.Room
import com.ilshat.node.data.db.AppDatabase
import com.ilshat.node.data.db.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(database: AppDatabase): LocalDataSource {
        return LocalDataSource(database)
    }
}