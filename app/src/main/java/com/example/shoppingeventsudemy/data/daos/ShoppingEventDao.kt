package com.example.shoppingeventsudemy.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppingeventsudemy.data.entities.ShoppingEvent
import com.example.shoppingeventsudemy.data.entities.ShoppingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingEventDao {
    @Insert
    suspend fun insert(shoppingEvent: ShoppingEvent)

    @Update
    suspend fun update(shoppingEvent: ShoppingEvent)

    @Delete
    suspend fun delete(shoppingEvent: ShoppingEvent)

    @Query("select e.id, e.name, e.initial_budget, e.event_date, e.completed, sum(i.price * i.quantity) as total_cost "+
        "from shopping_events e "+
        "left join shopping_items i on e.id = i.event_id " +
        "group by e.id"
    )
    fun getEvents() : Flow<List<ShoppingEvent>>

    @Query("select se.id, se.name, se.initial_budget, se.event_date, se.completed," +
        "(select sum(i.price * i.quantity) from shopping_items i where i.event_id = se.id) as total_cost," +
        "i.itemId, i.event_id, i.item_name, i.price, i.quantity " +
        "from shopping_events se left join shopping_items i on se.id = i.event_id "+
        "where se.id = :id")

    fun getEventAndItems(id: Long) : Flow<Map<ShoppingEvent, List<ShoppingItem>>>

}