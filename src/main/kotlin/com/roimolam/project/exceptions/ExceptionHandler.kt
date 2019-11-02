package com.roimolam.project.exceptions

import com.roimolam.project.data.ErrorInfo
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ApplicationException::class)
    @ResponseBody fun handleConflict(response: HttpServletResponse, e: ApplicationException): ErrorInfo? {
        e.printStackTrace()
        response.status = e.errorType.httpStatus
        e.errorType.errorInfo.message = e.message
        return e.errorType.errorInfo
    }

    @ExceptionHandler(InternalError::class)
    fun handleConflict(response: HttpServletResponse, e: Exception): String? {
        e.printStackTrace()
        response.status = 500
        return e.message
    }
}