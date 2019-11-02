package com.roimolam.project.logic

import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import javax.xml.catalog.Catalog

interface CatalogItemFacade {
    fun createCatalogItem(catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper

    fun getCatalogItem(id: Long): CatalogItemEntity?

    fun getCatalogItemPhoto(photoFileName: String): ByteArray

    fun getAllCatalogItems(): List<CatalogItemEntity>

    fun getCatalogItemsByFreeText(freeText: String): List<CatalogItemEntity>
}