package com.roimolam.project.logic

import com.roimolam.project.dal.UsersDAL
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.exceptions.ErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class UsersFacadeImpl (@Autowired val usersDAL: UsersDAL) : UsersFacade {

    override fun isUserLegitimate(email: String, password: String): Boolean {
        val user = usersDAL.getUser(email)
                ?: throw ApplicationException(ErrorType.NO_DATA_FOUND, "The account you're trying to authenticate doesn't exist")

        if (password == user.password) {
            return true
        }

        throw ApplicationException(ErrorType.NO_DATA_FOUND, "The password you inserted doesn't match the account's password")
    }
}