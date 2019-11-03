package com.roimolam.project.logic

import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity

interface CatalogItemFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper

    fun getCatalogItem(id: Long): CatalogItemEntity?

    fun getCatalogItemPhoto(photoFileName: String): String?

    fun getAllCatalogItems(): List<CatalogItemEntity>

    fun getCatalogItemsByFreeText(freeText: String): List<CatalogItemEntity>
}