package dev.abdl.fakecommerce.features.cart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.abdl.fakecommerce.features.cart.domain.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartEntity)

    @Update
    suspend fun updateCartItem(item: CartEntity)

    @Query("DELETE FROM cart WHERE productId = :productId")
    suspend fun deleteCartItemByProductId(productId: Int)

    @Query("DELETE FROM cart")
    suspend fun clearAllCart()
}