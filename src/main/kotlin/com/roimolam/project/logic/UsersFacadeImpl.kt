package com.roimolam.project.logic

import com.roimolam.project.dal.UsersDAL
import com.roimolam.project.data.entities.UserEntity
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.exceptions.ErrorType
import com.roimolam.project.utils.InputValidationUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller



@Controller
class UsersFacadeImpl (@Autowired val usersDAL: UsersDAL) : UsersFacade {

    override fun createUser(user: UserEntity): Long {

        if (!InputValidationUtils.isEmailValid(user.email)) {
            throw ApplicationException(ErrorType.WORNG_INPUT, "The email you inserted is invalid")
        }

        if (!InputValidationUtils.isPasswordValid(user.password)) {
            throw ApplicationException(ErrorType.WORNG_INPUT, "The password you inserted is invalid")
        }

        if (usersDAL.getUser(user.email) != null) {
            throw ApplicationException(ErrorType.ITEM_ALREADY_EXISTS, "The email you have entered already exists")
        }

        return usersDAL.createUser(user)
    }

    override fun isUserLegitimate(email: String, password: String): Boolean {
        val user = usersDAL.getUser(email)
                ?: throw ApplicationException(ErrorType.NO_DATA_FOUND, "The account you're trying to authenticate doesn't exist")

        if (password == user.password) {
            return true
        }

        throw ApplicationException(ErrorType.NO_DATA_FOUND, "The password you inserted doesn't match the account's password")
    }
}