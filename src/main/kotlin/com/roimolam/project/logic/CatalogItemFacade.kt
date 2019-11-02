package com.roimolam.project.logic

import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity

interface CatalogItemFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper

    fun getCatalogItem(id: Long): CatalogItemEntity?

    fun getAllCatalogItems(): List<CatalogItemEntity>
}