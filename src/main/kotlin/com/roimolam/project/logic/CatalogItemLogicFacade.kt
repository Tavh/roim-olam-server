package com.roimolam.project.logic

import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.enums.ItemType

interface CatalogItemLogicFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper

    fun getCatalogItem(id: Long): CatalogItemEntity?

    fun getAllCatalogItems(): List<CatalogItemEntity>

    fun getCatalogItemsByType(itemType: ItemType): List<CatalogItemEntity>

    fun getCatalogItemsByFreeText(freeText: String): List<CatalogItemEntity>
}