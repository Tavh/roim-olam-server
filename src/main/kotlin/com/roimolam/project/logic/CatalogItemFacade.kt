package com.roimolam.project.logic

import com.roimolam.project.entities.CatalogItemEntity

interface CatalogItemFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): Long
}