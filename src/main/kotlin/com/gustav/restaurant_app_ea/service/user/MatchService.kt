package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.user.MatchEntity
import com.gustav.restaurant_app_ea.model.user.UserEntity
import org.bson.types.ObjectId

interface MatchService {
    fun createMatch(user1:UserEntity,user2:UserEntity): MatchEntity

    fun getMatch(user1:UserEntity,user2:UserEntity): List<MatchEntity>

    fun updateMatchStatus(matchId: ObjectId, status:String): MatchEntity

    fun findMatchUser(user:UserEntity): List<UserEntity>
}