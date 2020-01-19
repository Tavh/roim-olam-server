package com.roimolam.project.logic

import com.roimolam.project.data.TextWrapper
import com.roimolam.project.enums.WebsiteComponent
import com.roimolam.project.enums.WebsiteComponentPart

interface WebsiteStaticContentLogicFacade {
    fun writeWebsiteComponentPart(websitePage: WebsiteComponent,
                                  websitePagePart: WebsiteComponentPart,
                                  websitePageText: TextWrapper): TextWrapper

    fun readWebsiteComponentPart(websiteComponent: WebsiteComponent,
                                 websiteComponentPart: WebsiteComponentPart): TextWrapper
}