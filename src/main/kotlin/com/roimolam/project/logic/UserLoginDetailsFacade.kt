package com.roimolam.project.logic

interface UserLoginDetailsFacade {

    fun isUserLegitimate(email: String, password: String): Boolean
}