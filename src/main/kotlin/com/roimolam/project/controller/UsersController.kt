package com.roimolam.project.controller

import com.roimolam.project.data.entities.UserEntity
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import com.roimolam.project.logic.UsersFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import com.roimolam.project.exceptions.ApplicationException
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping




@RestController
@RequestMapping("/admin/users")
class UsersController (@Autowired val usersFacade: UsersFacade) {

    @PostMapping("/create-user")
    @Throws(ApplicationException::class)
    fun createUserLoginDetails(@RequestBody user: UserEntity) {

        usersFacade.createUser(user)
    }

    @PostMapping("/login")
    @Throws(ApplicationException::class)
    fun login(request: HttpServletRequest,
              response: HttpServletResponse, @RequestBody user: UserEntity) {

        val email = user.email
        val password = user.password

        val isUserLegitimate = usersFacade.isUserLegitimate(email, password)

        if (isUserLegitimate) {

            request.session
            val cookie = Cookie("email", user.email)
            cookie.setPath("/")
            response.addCookie(cookie)

            response.status = 202
            response.setHeader("LoginStatus", "User : " + user.email + ", has logged in successfully")
        }
    }

    @GetMapping("/logout")
    @Throws(Throwable::class)
    fun logout(request: HttpServletRequest,
               response: HttpServletResponse) {

        var email = "No email detected"

        for (cookie in request.cookies) {
            if (cookie.name.equals("email")) {
                email = cookie.value
                val userCookie = Cookie("email", null)
                userCookie.setValue(null)
                userCookie.setPath(request.contextPath)
                userCookie.setMaxAge(0)
                response.addCookie(userCookie)
            }
        }

        request.session.invalidate()

        val cookie = Cookie("JSESSIONID", null)
        cookie.setValue(null)
        cookie.setPath(request.contextPath)
        cookie.setMaxAge(0)
        response.addCookie(cookie)

        response.setHeader("LogoutStatus", "User : $email has logged out successfully")
    }



}