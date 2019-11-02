package com.roimolam.project.logic

import com.roimolam.project.models.CatalogItemEntity

interface CatalogItemFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): Long
}