package com.roimolam.project.controller

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.data.TextWrapper
import com.roimolam.project.enums.UserType
import com.roimolam.project.enums.WebsitePagePart
import com.roimolam.project.enums.WebsitePage
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
    fun writeWebsiteComponentPart(@PathVariable websitePage: WebsitePage,
                             @PathVariable websitePagePart: WebsitePagePart,
                             @RequestBody websitePageText: TextWrapper): TextWrapper {
        return websiteStaticContentLogic.writeWebsiteComponentPart(websitePage,
                                                                   websitePagePart,
                                                                   websitePageText)
    }

    @GetMapping("/read/{websitePage}/{websitePagePart}")
    fun readWebsiteComponentPart(@PathVariable websitePage: WebsitePage,
                                 @PathVariable websitePagePart: WebsitePagePart): TextWrapper {
        return websiteStaticContentLogic.readWebsiteComponentPart(websitePage, websitePagePart)
    }
}
