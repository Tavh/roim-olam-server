package com.roimolam.project.logic

import com.roimolam.project.dal.TextDAL
import com.roimolam.project.data.TextWrapper
import com.roimolam.project.enums.WebsitePage
import com.roimolam.project.enums.WebsitePagePart
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import java.io.File

@Controller
class WebsiteStaticContentLogic (@Autowired private val textDAL: TextDAL) : WebsiteStaticContentLogicFacade {

    override fun writeWebsiteComponentPart(websitePage: WebsitePage,
                                           websitePagePart: WebsitePagePart,
                                           websitePageText: TextWrapper): TextWrapper {
        val websiteComponentValue = websitePage.value
        val websiteComponentPartValue = websitePagePart.value
        val filePostFix = "${File.separator}$websiteComponentValue${File.separator}$websiteComponentPartValue"
        return textDAL.writeToTextFile(filePostFix, websitePageText.text)
    }

    override fun readWebsiteComponentPart(websiteComponent: WebsitePage, websiteComponentPart: WebsitePagePart): TextWrapper {
        val websiteComponentValue = websiteComponent.value
        val websiteComponentPartValue = websiteComponentPart.value
        val filePostFix = "${File.separator}$websiteComponentValue${File.separator}$websiteComponentPartValue"
        return textDAL.readTextFromFile(filePostFix)
    }

}