package com.example.abcdialogue.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDataBaseDao {
    @Insert
    suspend fun insert(night: User)

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    suspend fun update(night: User)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from users WHERE id = :key")
    suspend fun get(key: Long): User

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM users")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllNights(): LiveData<List<User>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM users WHERE user_name =(:username)")
    suspend fun getPassword(username: String): User?
}