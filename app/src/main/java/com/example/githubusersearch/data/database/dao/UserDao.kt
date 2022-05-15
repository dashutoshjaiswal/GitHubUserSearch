package com.example.githubusersearch.data.database.dao

import androidx.room.*
import com.example.githubusersearch.data.database.model.User
import kotlinx.coroutines.flow.Flow

/**
 * This class provides general DAO for [User]
 * */
@Dao
abstract class UserDao {

    // insert or update if exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUsers(users: List<User>)

    @Query("DELETE FROM User")
    abstract fun delete()

    @Transaction
    @Query("SELECT * FROM User")
    abstract fun getAllUSer(): Flow<List<User>>

}