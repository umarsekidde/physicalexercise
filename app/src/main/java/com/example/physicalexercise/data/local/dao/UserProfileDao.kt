package com.example.physicalexercise.data.local.dao

import androidx.room.*
import com.example.physicalexercise.data.local.entities.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profiles WHERE id = :userId")
    fun getUserProfile(userId: String): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfileEntity)

    @Update
    suspend fun updateUserProfile(userProfile: UserProfileEntity)

    @Delete
    suspend fun deleteUserProfile(userProfile: UserProfileEntity)

    @Query("SELECT * FROM user_profiles")
    fun getAllUserProfiles(): Flow<List<UserProfileEntity>>
} 