package com.roimolam.project.controller

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.data.TextWrapper
import com.roimolam.project.enums.*
import com.roimolam.project.logic.WebsiteStaticContentLogicFacade
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin("*", allowCredentials = "true", allowedHeaders = ["*"],
        methods = [ RequestMethod.POST,
                    RequestMethod.GET,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.PUT,
                    RequestMethod.PATCH ],
        exposedHeaders = [ "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "errorMessage" ])
@RestController
@RequestMapping("/website-static-content")
class WebsiteStaticContentController (@Autowired private val websiteStaticContentLogic: WebsiteStaticContentLogicFacade) {

    @UserPermission(userType = UserType.ADMIN)
    @PutMapping("/write/{websitePage}/{websitePagePart}")
    fun writeWebsiteComponentPart(@PathVariable websitePage: WebsiteComponent,
                             @PathVariable websitePagePart: WebsiteComponentPart,
                             @RequestBody websitePageText: TextWrapper): TextWrapper {
        return websiteStaticContentLogic.writeWebsiteComponentPart(websitePage,
                                                                   websitePagePart,
                                                                   websitePageText)
    }

    @GetMapping("/read/{websitePage}/{websitePagePart}")
    fun readWebsiteComponentPart(@PathVariable websitePage: WebsiteComponent,
                                 @PathVariable websitePagePart: WebsiteComponentPart): TextWrapper {
        return websiteStaticContentLogic.readWebsiteComponentPart(websitePage, websitePagePart)
    }
}
