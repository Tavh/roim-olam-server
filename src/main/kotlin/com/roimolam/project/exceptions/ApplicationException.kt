package com.roimolam.project.exceptions

class ApplicationException : Exception {

    var errorType: ErrorType

    constructor(errorType: ErrorType, message: String) : super(message) {
        this.errorType = errorType
    }

    constructor(errorType: ErrorType, message: String, e: Exception) : super(message, e) {
        this.errorType = errorType
    }
}