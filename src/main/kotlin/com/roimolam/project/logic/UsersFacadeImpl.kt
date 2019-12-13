package com.roimolam.project.logic

import com.roimolam.project.dal.UsersDAL
import com.roimolam.project.data.entities.UserEntity
import com.roimolam.project.enums.UserType
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.enums.ErrorType
import com.roimolam.project.utils.InputValidationUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class UsersFacadeImpl (@Autowired val usersDAL: UsersDAL) : UsersFacade {

    override fun createUser(user: UserEntity): Long {
        user.apply {
            if (userType == UserType.ADMIN) {
                throw ApplicationException(ErrorType.WRONG_INPUT, "An admin type user cannot be created this way")
            }

            if (!InputValidationUtils.isEmailValid(email)) {
                throw ApplicationException(ErrorType.WRONG_INPUT, "The email you inserted is invalid")
            }

            if (!InputValidationUtils.isPasswordValid(password)) {
                throw ApplicationException(ErrorType.WRONG_INPUT, "The password you inserted is invalid")
            }

            if (usersDAL.getUser(email) != null) {
                throw ApplicationException(ErrorType.ITEM_ALREADY_EXISTS, "The email you have entered already exists")
            }

            password = password.hashCode().toString()

            return usersDAL.createUser(this)
        }
    }

    override fun getUser(email: String): UserEntity? {
        return usersDAL.getUser(email)
                ?: throw ApplicationException(ErrorType.NO_DATA_FOUND,
                                              "The user you requested ($email) could not be found")

    }

    override fun isUserLegitimate(email: String, password: String?): Boolean {
        val user = usersDAL.getUser(email)
                ?: throw ApplicationException(ErrorType.NO_DATA_FOUND,
                                              "The account you're trying to authenticate doesn't exist")

        if (password.hashCode().toString() == user.password) {
            return true
        }

        throw ApplicationException(ErrorType.NO_DATA_FOUND,
                                   "The password you inserted doesn't match the account's password")
    }
}