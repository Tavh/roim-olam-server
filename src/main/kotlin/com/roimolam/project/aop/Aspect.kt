package com.roimolam.project.aop

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.enums.UserType
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.exceptions.ErrorType
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component


@Aspect
@Component
class Aspect {

    @Around("@annotation(userPermissionAnnotation)")
    @Throws(Throwable::class)
    fun checkUserPermission(joinPoint: ProceedingJoinPoint,
                            userPermissionAnnotation: UserPermission): Any? {
        if (userPermissionAnnotation.userType == UserType.ADMIN) {
            throw ApplicationException(ErrorType.WRONG_INPUT, "You are not an admin")
        }

        return joinPoint.proceed()
    }
}