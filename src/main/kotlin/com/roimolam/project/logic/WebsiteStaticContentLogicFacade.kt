package com.roimolam.project.logic

import com.roimolam.project.data.TextWrapper
import com.roimolam.project.enums.WebsitePage
import com.roimolam.project.enums.WebsitePagePart

interface WebsiteStaticContentLogicFacade {
    fun writeWebsiteComponentPart(websitePage: WebsitePage,
                                  websitePagePart: WebsitePagePart,
                                  websitePageText: TextWrapper): TextWrapper

    fun readWebsiteComponentPart(websitePage: WebsitePage,
                                 websitePagePart: WebsitePagePart): TextWrapper
}