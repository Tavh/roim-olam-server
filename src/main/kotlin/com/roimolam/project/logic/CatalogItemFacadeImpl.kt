package com.roimolam.project.logic

import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.entities.CatalogItemEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CatalogItemFacadeImpl (@Autowired val catalogItemDAL: CatalogItemDAL) : CatalogItemFacade {

    override fun createCatalogItem(catalogItemEntity: CatalogItemEntity): Long {
       return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }
}