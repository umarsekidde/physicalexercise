package com.example.physicalexercise.data.repository

import com.example.physicalexercise.data.local.dao.UserProfileDao
import com.example.physicalexercise.data.local.entities.UserProfileEntity
import com.example.physicalexercise.data.models.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.UUID

class UserProfileRepository(private val userProfileDao: UserProfileDao) {

    fun getUserProfile(userId: String): Flow<UserProfile?> {
        return userProfileDao.getUserProfile(userId).map { entity ->
            entity?.toUserProfile()
        }
    }

    fun getAllUserProfiles(): Flow<List<UserProfile>> {
        return userProfileDao.getAllUserProfiles().map { entities ->
            entities.map { it.toUserProfile() }
        }
    }

    suspend fun createUserProfile(
        name: String,
        age: Int,
        weight: Float,
        height: Float,
        gender: String,
        goal: String,
        dietaryPreference: String
    ): UserProfile {
        val userProfile = UserProfileEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            age = age,
            weight = weight,
            height = height,
            gender = gender,
            goal = com.example.physicalexercise.data.models.FitnessGoal.valueOf(goal),
            dietaryPreference = com.example.physicalexercise.data.models.DietaryPreference.valueOf(dietaryPreference),
            createdAt = Date(),
            updatedAt = Date()
        )
        userProfileDao.insertUserProfile(userProfile)
        return userProfile.toUserProfile()
    }

    suspend fun updateUserProfile(userProfile: UserProfile) {
        userProfileDao.updateUserProfile(userProfile.toEntity())
    }

    suspend fun deleteUserProfile(userProfile: UserProfile) {
        userProfileDao.deleteUserProfile(userProfile.toEntity())
    }

    private fun UserProfileEntity.toUserProfile(): UserProfile {
        return UserProfile(
            id = id,
            name = name,
            age = age,
            weight = weight,
            height = height,
            gender = gender,
            goal = goal,
            dietaryPreference = dietaryPreference,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    private fun UserProfile.toEntity(): UserProfileEntity {
        return UserProfileEntity(
            id = id,
            name = name,
            age = age,
            weight = weight,
            height = height,
            gender = gender,
            goal = goal,
            dietaryPreference = dietaryPreference,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
} 