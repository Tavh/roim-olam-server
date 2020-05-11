package com.roimolam.project.enums

import com.roimolam.project.data.ErrorInfo
import javax.print.attribute.standard.Severity

enum class ErrorType(val httpStatus: Int, val errorInfo: ErrorInfo) {

    ITEM_ALREADY_EXISTS(203, ErrorInfo("A unique item cannot be re-inserted",
                                               "BUSINESS-ERROR")),
    NO_DATA_FOUND(209, ErrorInfo("No data found",
                                         "INFO")),

    WRONG_INPUT(207, ErrorInfo("Wrong input received",
                                            "BUSINESS_ERROR")),

    USER_NOT_PERMITTED( 403, ErrorInfo("User does not have sufficient permissions",
                                                    "SECURITY")),

    UPLOAD_FAILED( 510, ErrorInfo("Server failed to perform requested upload",
                                                        "ERROR"))
}

