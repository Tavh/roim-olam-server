package com.roimolam.project.aspects

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.enums.UserType
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.enums.ErrorType
import com.roimolam.project.logic.UsersLogicFacade
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@Aspect
@Component
class UserPermissionsAspect(@Autowired val usersFacade: UsersLogicFacade) {

    @Around("@annotation(userPermissionAnnotation)")
    @Throws(Throwable::class)
    fun userPermissionAdvice(joinPoint: ProceedingJoinPoint,
                            userPermissionAnnotation: UserPermission): Any? {
        if (userPermissionAnnotation.userType == UserType.ADMIN) {
            val request =
                    (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request

            val userEmail = request.getHeader(USER_EMAIL_HEADER_NAME)
                    ?: throw ApplicationException(ErrorType.USER_NOT_PERMITTED,
                                                  "Request does not contain $USER_EMAIL_HEADER_NAME header!")

            usersFacade.getUser(userEmail)!!.apply {
                if (userType != UserType.ADMIN) {
                    throw ApplicationException(ErrorType.USER_NOT_PERMITTED, "$userEmail is not an admin!")
                }
            }
        }

        return joinPoint.proceed()
    }

    companion object {
        private const val USER_EMAIL_HEADER_NAME = "user-email"
    }
}