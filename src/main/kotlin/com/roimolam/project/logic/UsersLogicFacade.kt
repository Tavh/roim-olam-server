package com.roimolam.project.logic

import com.roimolam.project.data.entities.UserEntity

interface UsersLogicFacade {

    fun createUser(user: UserEntity): Long
    fun isUserLegitimate(email: String, password: String?): Boolean
    fun getUser(email: String): UserEntity?
}