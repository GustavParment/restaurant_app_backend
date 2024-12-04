package com.gustav.restaurant_app_ea.service.user

import com.gustav.restaurant_app_ea.model.user.MatchEntity
import com.gustav.restaurant_app_ea.model.user.UserEntity
import org.bson.types.ObjectId

interface MatchService {
    fun createMatch(user1:String,user2:String): MatchEntity

    fun getMatch(user1:String,user2:String): List<MatchEntity>

    fun updateMatchStatus(matchId: ObjectId, status:String): MatchEntity

    fun findMatchUser(user:UserEntity): List<UserEntity>
}