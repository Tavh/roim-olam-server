package com.roimolam.project.logic

import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.exceptions.ErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CatalogItemFacadeImpl (@Autowired val catalogItemDAL: CatalogItemDAL) : CatalogItemFacade {

    override fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
       return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }

    override fun getCatalogItem(id: Long): CatalogItemEntity? {
        return catalogItemDAL.getCatalogItem(id)
    }

    override fun getAllCatalogItems(): List<CatalogItemEntity> {
        val allCatalogItems = catalogItemDAL.getAllCatalogItems()
        if (allCatalogItems.isEmpty()) {
            throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
        }

        return allCatalogItems
    }
}