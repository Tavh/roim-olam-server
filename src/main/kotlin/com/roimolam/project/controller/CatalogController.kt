package com.roimolam.project.controller

import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.PhotoFileNameWrapper
import com.roimolam.project.logic.CatalogItemFacade
import com.roimolam.project.data.entities.CatalogItemEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage

@RestController
@RequestMapping("/catalog")
class CatalogController (@Autowired val catalogItemFacade: CatalogItemFacade,
                         @Autowired val photoUploadingDAL: PhotoDAL) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-catalog-item")
    fun createCatalogItem(@RequestBody catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
         return catalogItemFacade.createCatalogItem(catalogItemEntity)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-catalog-item-photo")
    fun saveCatalogItemPhoto(@ModelAttribute photo: MultipartFile): PhotoFileNameWrapper {
        return photoUploadingDAL.saveCatalogItemPhoto(photo)
    }

    @GetMapping("/get-catalog-item/{id}")
    fun getCatalogItem(@PathVariable id: Long): CatalogItemEntity? {
        return catalogItemFacade.getCatalogItem(id)
    }

    @GetMapping("/get-catalog-item-photo")
    fun getCatalogItemPhoto(@RequestParam photoFileName: String): ByteArray {
        return catalogItemFacade.getCatalogItemPhoto(photoFileName)
    }

    @GetMapping("/get-all-catalog-items")
    fun getAllCatalogItems(): List<CatalogItemEntity> {
        return catalogItemFacade.getAllCatalogItems()
    }

    @GetMapping("get-catalog-items-by-free-text")
    fun getCatalogItemsByFreeText(@RequestParam freeText: String): List<CatalogItemEntity> {
        return catalogItemFacade.getCatalogItemsByFreeText(freeText)
    }
}
