package com.roimolam.project.annotations

import com.roimolam.project.enums.UserType

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPermission (val userType: UserType)