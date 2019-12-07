package com.roimolam.project.annotations

import com.roimolam.project.enums.UserType
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPermission (val userType: UserType) {
}