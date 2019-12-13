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
class CatalogItemLogic (@Autowired val catalogItemDAL: CatalogItemDAL,
                        @Autowired val photoDAL: PhotoDAL) : CatalogItemLogicFacade {

    override fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
       return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }

    override fun getCatalogItem(id: Long): CatalogItemEntity? {
        return catalogItemDAL.getCatalogItem(id)
    }

    override fun getCatalogItemPhoto(photoFileName: String): String? {
        return photoDAL.getCatalogItemPhoto(photoFileName)
    }

    override fun getAllCatalogItems(): List<CatalogItemEntity> {
        catalogItemDAL.getAllCatalogItems().apply {
            if (isEmpty()) {
                throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
            }

            forEach { c -> c.photoBase64String = getCatalogItemPhoto(c.photoFileName) }

            return this
        }

    }

    override fun getCatalogItemsByType(itemType: ItemType): List<CatalogItemEntity> {
        return catalogItemDAL.getCatalogItemsByType(itemType).apply {
            forEach { c -> c.photoBase64String = getCatalogItemPhoto(c.photoFileName) }
        }
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