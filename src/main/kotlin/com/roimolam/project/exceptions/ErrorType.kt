package com.roimolam.project.exceptions

enum class ErrorType(val internalErrorCode: Int, val internalMessage: String) {

    ITEM_ALREADY_EXISTS(406, internalMessage = "item already exists")
}

