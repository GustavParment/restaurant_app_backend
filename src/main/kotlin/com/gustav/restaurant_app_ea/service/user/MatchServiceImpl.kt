package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.config.exceptionhandling.UserNotFoundException
import com.gustav.restaurant_app_ea.model.user.MatchEntity
import com.gustav.restaurant_app_ea.model.user.MatchStatus
import com.gustav.restaurant_app_ea.model.user.UserEntity
import com.gustav.restaurant_app_ea.repository.user.MatchRepository
import com.gustav.restaurant_app_ea.repository.user.UserRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class MatchServiceImpl(
    private val userRepository: UserRepository,
    private val matchRepository: MatchRepository
): MatchService
{
    override fun createMatch(userId1: ObjectId, userId2: ObjectId): MatchEntity {
        val user1 = userRepository.findById(userId1)
            .orElseThrow { UserNotFoundException("User with ID $userId1 not found") }
        val user2 = userRepository.findById(userId2)
            .orElseThrow { UserNotFoundException("User with ID $userId2 not found") }

        if (!hasCommonHobby(user1, user2) && !hasCommonFood(user1, user2)) {
            throw IllegalArgumentException("Users do not have common hobbies or favorite foods")
        }

        val matchEntity = MatchEntity(
            userId1 = user1.id ?: throw UserNotFoundException("User not found"),
            userId2 = user2.id ?: throw UserNotFoundException("User not found"),
            matchStatus = MatchStatus.WAITING,
        )
        val savedMatch = matchRepository.save(matchEntity)

        val usersToUpdate = listOf(user1, user2)
        val updatedUsers = usersToUpdate.map { updateUserMatchList(it, savedMatch.id) }
        userRepository.saveAll(updatedUsers)

        return savedMatch
    }


    override fun getMatch(userId1: ObjectId, userId2: ObjectId): List<MatchEntity> {
        return matchRepository.findAllByUserId1AndUserId2(userId1, userId2) +
                matchRepository.findAllByUserId1AndUserId2(userId2, userId1)
    }

    override fun updateMatchStatus(matchId: ObjectId, status: String): MatchEntity {
        val matchEntity = matchRepository.findById(matchId)
            .orElseThrow {
                IllegalArgumentException("Match not found with ID: $matchId")
            }

        val updateStatus = when (status.uppercase()){
            "WAITING" -> MatchStatus.WAITING
            "ACCEPTED" -> MatchStatus.ACCEPTED
            "DECLINED" -> MatchStatus.DECLINED
            else -> throw IllegalArgumentException("Unknown status: $status")
        }
        val updatedMatch = matchEntity.copy(
            matchStatus = updateStatus
        )
        return matchRepository.save(updatedMatch)
    }

    override fun findMatchUser(user: UserEntity): List<UserEntity> {
        val userId = user.id ?: throw UserNotFoundException("User not found")
        val match = matchRepository.findAllByUserId1OrUserId2(userId,userId)

        val matchUserId = match.map {
            match ->
            if(match.userId1 == userId) match.userId2 else match.userId1
        }

        return  userRepository.findAllById(matchUserId)
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

    private fun updateUserMatchList(user: UserEntity, matchId: ObjectId): UserEntity {
        return user.copy(
            matchListId = (user.matchListId ?: emptyList()) + matchId
        )
    }

}