package com.roimolam.project.logic

import com.roimolam.project.constants.*
import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.data.CatalogItemsWrapper
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.enums.ErrorType
import com.roimolam.project.enums.ItemType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional

@Controller
class CatalogItemLogic (@Autowired val catalogItemDAL: CatalogItemDAL,
                        @Autowired val photoDAL: PhotoDAL) : CatalogItemLogicFacade {

    @Transactional
    override fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
        photoDAL.saveCatalogItemPhoto(catalogItemEntity.photo)

        if (catalogItemEntity.description.length > MAX_CATALOG_ITEM_DESC_LENGTH) {
            throw ApplicationException(ErrorType.WRONG_INPUT, "Item description must consist of up to " +
                                                                "$MAX_CATALOG_ITEM_DESC_LENGTH characters")
        }
        return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }

    override fun getCatalogItem(id: Long): CatalogItemEntity? {
        return catalogItemDAL.getCatalogItem(id)
    }

    override fun getAllCatalogItems(): List<CatalogItemEntity> {
        return catalogItemDAL.getAllCatalogItems()
    }

    override fun getCatalogItemsByType(itemType: ItemType, page: Int?): CatalogItemsWrapper {
        return catalogItemDAL.getCatalogItemsByType(itemType, page)
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

    override fun getCatalogItemsByBrand(itemType: ItemType, brand: String): List<CatalogItemEntity> {
        return catalogItemDAL.getCatalogItemsByBrand(itemType, brand)
    }
}