package com.roimolam.project.exceptions

import com.roimolam.project.data.ErrorInfo
import javax.print.attribute.standard.Severity

enum class ErrorType(val httpStatus: Int, val errorInfo: ErrorInfo) {

    ITEM_ALREADY_EXISTS(406, ErrorInfo("A unique item cannot be re-inserted",
                                                "BUSINESS-ERROR"))
}

