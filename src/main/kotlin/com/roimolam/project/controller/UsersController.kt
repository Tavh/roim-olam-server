package com.roimolam.project.controller

import com.roimolam.project.data.entities.UserEntity
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import com.roimolam.project.logic.UsersLogicFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import com.roimolam.project.exceptions.ApplicationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping

@CrossOrigin("*", allowCredentials = "true", allowedHeaders = ["*"],
        methods = [ RequestMethod.POST,
            RequestMethod.GET,
            RequestMethod.HEAD,
            RequestMethod.OPTIONS,
            RequestMethod.PUT,
            RequestMethod.PATCH ],
        exposedHeaders = [ "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "errorMessage" ])
@RestController
@RequestMapping("/users")
class UsersController (@Autowired val usersFacade: UsersLogicFacade) {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-user")
    @Throws(ApplicationException::class)
    fun createUserLoginDetails(@RequestBody user: UserEntity) {
        usersFacade.createUser(user)
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    @Throws(ApplicationException::class)
    fun login(request: HttpServletRequest,
              response: HttpServletResponse, @RequestBody user: UserEntity): UserEntity? {

        val email = user.email
        val password = user.password
        val isUserLegitimate = usersFacade.isUserLegitimate(email, password)

        if (isUserLegitimate) {
            request.session
            Cookie(USER_EMAIL_COOKIE_NAME, user.email).apply {
                path = "/"
                response.addCookie(this)
            }

        }

        return usersFacade.getUser(email)
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest,
               response: HttpServletResponse) {

        if (request.cookies != null) {
            request.cookies.forEach {
                if (it.name == USER_EMAIL_COOKIE_NAME) {
                    Cookie(USER_EMAIL_COOKIE_NAME, null).apply {
                        value = null
                        path = request.contextPath
                        maxAge = 0
                        response.addCookie(this)
                    }
                }
            }
        }

        val cookie = Cookie(SERVER_SESSION_COOKIE_NAME, null)
        cookie.path = request.contextPath
        cookie.maxAge = 0
        response.addCookie(cookie)
        request.session.invalidate()
    }

    companion object {
        private const val SERVER_SESSION_COOKIE_NAME = "JSESSIONID"
        private const val USER_EMAIL_COOKIE_NAME = "email"
    }
}