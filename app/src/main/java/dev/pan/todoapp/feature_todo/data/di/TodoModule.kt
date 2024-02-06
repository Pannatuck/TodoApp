package dev.pan.todoapp.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.pan.todoapp.feature_todo.data.local.TodoDao
import dev.pan.todoapp.feature_todo.data.local.TodoDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {
    @Provides
    fun providesRoomDao(database: TodoDatabase): TodoDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ):TodoDatabase{
        return Room.databaseBuilder(
            appContext.applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()
    }
}