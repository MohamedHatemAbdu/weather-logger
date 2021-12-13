package com.me.presentation.base.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.me.data.base.AppDatabase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideDatabaseName(): String {
        return "PatientsLoggerAppDatabase"
    }

    @Provides
    @Singleton
    fun provideDatabase(dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }


}
