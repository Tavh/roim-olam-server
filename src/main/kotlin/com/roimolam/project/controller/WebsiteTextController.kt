package com.roimolam.project.controller

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.dal.TextDAL
import com.roimolam.project.data.WebsiteComponentTextWrapper
import com.roimolam.project.enums.UserType
import com.roimolam.project.enums.WebsiteComponentPart
import com.roimolam.project.enums.WebsiteComponent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.File

@CrossOrigin("*", allowCredentials = "true", allowedHeaders = ["*"],
        methods = [ RequestMethod.POST,
                    RequestMethod.GET,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.PUT,
                    RequestMethod.PATCH ],
        exposedHeaders = [ "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "errorMessage" ])
@RestController
@RequestMapping("/website-content")
class WebsiteTextController (@Autowired val textDAL: TextDAL) {

    @UserPermission(userType = UserType.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/write/{websiteComponent}/{websiteComponentPart}")
    fun saveCatalogItemPhoto(@PathVariable websiteComponent: WebsiteComponent,
                             @PathVariable websiteComponentPart: WebsiteComponentPart,
                             @RequestBody websiteComponentText: WebsiteComponentTextWrapper) {
        val websiteComponentValue = websiteComponent.value
        val websiteComponentPartValue = websiteComponentPart.value
        val filePostFix = "${File.separator}$websiteComponentValue${File.separator}$websiteComponentPartValue"
        textDAL.writeToTextFile(filePostFix, websiteComponentText.text)
    }
}
