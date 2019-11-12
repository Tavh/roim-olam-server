package com.roimolam.project.logic

interface UsersFacade {

    fun isUserLegitimate(email: String, password: String): Boolean
}