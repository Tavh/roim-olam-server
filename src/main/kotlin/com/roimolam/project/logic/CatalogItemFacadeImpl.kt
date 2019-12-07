package com.roimolam.project.logic

import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.enums.ErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class CatalogItemFacadeImpl (@Autowired val catalogItemDAL: CatalogItemDAL,
                             @Autowired val photoDAL: PhotoDAL) : CatalogItemFacade {

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
        val allCatalogItems = catalogItemDAL.getAllCatalogItems()
        if (allCatalogItems.isEmpty()) {
            throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
        }

        allCatalogItems.forEach { c -> c.photoBase64String = getCatalogItemPhoto(c.photoFileName) }

        return allCatalogItems
    }

    override fun getCatalogItemsByFreeText(freeText: String): List<CatalogItemEntity> {
        val allCatalogItems = catalogItemDAL.getAllCatalogItems()

        val filteredCatalogItems = allCatalogItems.filter { c -> c.title.contains(freeText, ignoreCase = true)
                                                                 || c.description.contains(freeText, ignoreCase = true) }

        if (allCatalogItems.isEmpty()) {
            throw ApplicationException(ErrorType.NO_DATA_FOUND,
                                       "Couldn't find any catalog items with the text $freeText")
        }

        return filteredCatalogItems
    }
}