package com.example.shoppingeventsudemy.hiltmodules

import android.content.Context
import com.example.shoppingeventsudemy.data.ShoppingDatabase
import com.example.shoppingeventsudemy.data.daos.ShoppingEventDao
import com.example.shoppingeventsudemy.data.daos.ShoppingItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideShoppingEventDao(@ApplicationContext context: Context) : ShoppingEventDao {
        return ShoppingDatabase.getDatabase(context).shoppingEventDao()
    }

    @Provides
    fun provideShoppingItemDao(@ApplicationContext context: Context) : ShoppingItemDao {
        return ShoppingDatabase.getDatabase(context).shoppingItemDao()
    }
}