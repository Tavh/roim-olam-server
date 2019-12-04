package com.roimolam.project.utils

import java.util.regex.Pattern

class InputValidationUtils {

    companion object {

        private const val PASSWORD_REGEX = "[a-zA-Z0-9]{4,12}"
        private const val EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
        private const val NAME_REGEX = "[a-zA-Z ,.'-]{2,}"
        private const val DATE_REGEX = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"

        fun isPasswordValid(password: String): Boolean {

            return password.matches(PASSWORD_REGEX.toRegex())
        }

        fun isEmailValid(email: String): Boolean {

            val emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE)

            val matcher = emailPattern.matcher(email)

            return matcher.find()
        }

        fun isNameValid(name: String): Boolean {

            return name.matches(NAME_REGEX.toRegex())
        }


        fun isDateValid(date: String): Boolean {

            return date.matches(DATE_REGEX.toRegex())
        }
    }
}