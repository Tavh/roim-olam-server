package com.roimolam.project.controller

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.PhotoUploadStatusWrapper
import com.roimolam.project.logic.CatalogItemLogicFacade
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.data.entities.CatalogItemsWrapper
import com.roimolam.project.enums.ItemType
import com.roimolam.project.enums.UserType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin("*", allowCredentials = "true", allowedHeaders = ["*"],
        methods = [ RequestMethod.POST,
                    RequestMethod.GET,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.PUT,
                    RequestMethod.PATCH ],
        exposedHeaders = [ "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "errorMessage" ])
@RestController
@RequestMapping("/test")
class TestController () {

    @GetMapping
    fun getCatalogItemsByFreeText(): String {
        return "Working!"
    }
}
