package com.example.shoppingeventsudemy.data.repos

import com.example.shoppingeventsudemy.data.daos.ShoppingEventDao
import com.example.shoppingeventsudemy.data.entities.ShoppingEvent
import com.example.shoppingeventsudemy.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ShoppingEventRepository {
    suspend fun insert(shoppingEvent: ShoppingEvent)
    suspend fun update(shoppingEvent: ShoppingEvent)
    suspend fun delete(shoppingEvent: ShoppingEvent)
    fun getEvents() : Flow<List<ShoppingEvent>>
    fun getEventAndItems(id: Long) : Flow<Map<ShoppingEvent, List<ShoppingItem>>>
}

class ShoppingEventRepositoryImpl @Inject constructor(
    private val shoppingEventDao: ShoppingEventDao
) : ShoppingEventRepository {
    override suspend fun insert(shoppingEvent: ShoppingEvent) = shoppingEventDao.insert(shoppingEvent)

    override suspend fun update(shoppingEvent: ShoppingEvent) = shoppingEventDao.update(shoppingEvent)

    override suspend fun delete(shoppingEvent: ShoppingEvent) = shoppingEventDao.delete(shoppingEvent)

    override fun getEvents(): Flow<List<ShoppingEvent>> = shoppingEventDao.getEvents()
    override fun getEventAndItems(id: Long): Flow<Map<ShoppingEvent, List<ShoppingItem>>> = shoppingEventDao.getEventAndItems(id)

}