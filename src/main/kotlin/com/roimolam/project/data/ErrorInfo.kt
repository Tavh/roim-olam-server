package com.roimolam.project.data

data class ErrorInfo(val generalInfo: String,
                     val classification: String) {


    var message: String? = null

    constructor(message: String, generalInfo: String, classification: String): this(generalInfo, classification) {
        this.message = message
    }
}