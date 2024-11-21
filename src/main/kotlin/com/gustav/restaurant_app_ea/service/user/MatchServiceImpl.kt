package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.user.MatchEntity
import com.gustav.restaurant_app_ea.model.user.MatchStatus
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.repository.user.MatchRepository
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import org.bson.types.ObjectId

class MatchServiceImpl(
    private val userRepository: UserRepository,
    private val matchRepository: MatchRepository
): MatchService
{
    override fun createMatch(user1: UserEntity, user2: UserEntity): MatchEntity {
        val matchEntity = MatchEntity(
            userId1 = user1.id ?: throw UserNotFoundException("User not found "),
            userId2 = user2.id ?: throw UserNotFoundException("User not found "),
            matchStatus = MatchStatus.WAITING,
            commonHobby = findCommonHobby(user1,user2),
            commonFood = findCommonFood(user1,user2)
        )
        return matchRepository.save(matchEntity)
    }

    override fun getMatch(user1: UserEntity, user2: UserEntity): List<MatchEntity> {
        TODO("Not yet implemented")
    }

    override fun updateMatchStatus(matchId: ObjectId, status: String): MatchEntity {
        TODO("Not yet implemented")
    }

    override fun findMatchUser(user: UserEntity): List<UserEntity> {
        TODO("Not yet implemented")
    }

    private fun findCommonFood(user1: UserEntity, user2: UserEntity): List<String> {
        return user1
            .profile?.firstOrNull()?.favoriteFood?.intersect(
                (user2.profile?.firstOrNull()?.favoriteFood ?: emptyList()).toSet()
            )?.toList() ?: emptyList()
    }

    private fun findCommonHobby(user1: UserEntity, user2: UserEntity): List<String> {
        return user1
            .profile?.firstOrNull()?.hobbies?.intersect(
                (user2.profile?.firstOrNull()?.hobbies ?: emptyList()).toSet()
            )?.toList() ?: emptyList()
    }

    private fun hasCommonHobby(user1: UserEntity, user2: UserEntity): Boolean {
        return findCommonHobby(user1, user2).isNotEmpty()
    }

    private fun hasCommonFood(user1: UserEntity, user2: UserEntity): Boolean{
        return findCommonFood(user1, user2).isNotEmpty()
    }
}