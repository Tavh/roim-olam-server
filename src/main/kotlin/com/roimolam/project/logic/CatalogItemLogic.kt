package com.roimolam.project.logic

import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.enums.ErrorType
import com.roimolam.project.enums.ItemType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CatalogItemLogic (@Autowired val catalogItemDAL: CatalogItemDAL) : CatalogItemLogicFacade {

    override fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
       return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }

    override fun getCatalogItem(id: Long): CatalogItemEntity? {
        return catalogItemDAL.getCatalogItem(id)
    }

    override fun getAllCatalogItems(): List<CatalogItemEntity> {
        return catalogItemDAL.getAllCatalogItems()
    }

    override fun getCatalogItemsByType(itemType: ItemType): List<CatalogItemEntity> {
        return catalogItemDAL.getCatalogItemsByType(itemType)
    }

    override fun getCatalogItemsByFreeText(freeText: String): List<CatalogItemEntity> {
        catalogItemDAL.getAllCatalogItems().apply {
            val filteredCatalogItems = filter { c -> c.title.contains(freeText, ignoreCase = true)
                                                                || c.description.contains(freeText, ignoreCase = true) }

            if (isEmpty()) {
                throw ApplicationException(ErrorType.NO_DATA_FOUND,
                                           "Couldn't find any catalog items with the text $freeText")
            }

            return filteredCatalogItems
        }
    }
}