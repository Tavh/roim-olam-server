package com.roimolam.project.exceptions

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ApplicationException::class)
    @ResponseBody fun handleConflict(response: HttpServletResponse, e: ApplicationException): String? {
        e.printStackTrace()
        response.status = e.errorType.internalErrorCode
        return e.message
    }

    @ExceptionHandler(InternalError::class)
    fun handleConflict(response: HttpServletResponse, e: Exception): String? {
        e.printStackTrace()
        response.status = 500
        return e.message
    }
}