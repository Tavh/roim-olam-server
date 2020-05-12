package com.roimolam.project.logic

import com.roimolam.project.data.CatalogItemDeleteStatusWrapper
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.data.CatalogItemsWrapper
import com.roimolam.project.enums.ItemType
import org.springframework.web.bind.annotation.PathVariable

interface CatalogItemLogicFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper

    fun getCatalogItem(id: Long): CatalogItemEntity?

    fun getAllCatalogItems(): List<CatalogItemEntity>

    fun getCatalogItemsByType(itemType: ItemType, page: Int?): CatalogItemsWrapper

    fun getCatalogItemsByFreeText(freeText: String): List<CatalogItemEntity>

    fun getCatalogItemsByBrand(itemType: ItemType, brand: String): List<CatalogItemEntity>

    fun deleteCatalogItem(@PathVariable id: Long): CatalogItemDeleteStatusWrapper
}