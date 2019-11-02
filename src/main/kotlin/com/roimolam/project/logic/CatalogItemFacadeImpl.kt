package com.roimolam.project.logic

import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.models.CatalogItemEntity
import org.springframework.beans.factory.annotation.Autowired

class CatalogItemFacadeImpl (@Autowired val catalogItemDAL: CatalogItemDAL) : CatalogItemFacade {

    override fun createCatalogItem(catalogItemEntity: CatalogItemEntity): Long {
       return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }
}