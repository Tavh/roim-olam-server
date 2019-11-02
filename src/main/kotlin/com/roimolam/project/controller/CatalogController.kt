package com.roimolam.project.controller

import com.roimolam.project.dal.PhotoUploadingDAL
import com.roimolam.project.logic.CatalogItemFacade
import com.roimolam.project.entities.CatalogItemEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/catalog")
class CatalogController (@Autowired val catalogItemFacade: CatalogItemFacade,
                         @Autowired val photoUploadingDAL: PhotoUploadingDAL) {

    @PostMapping("/create-catalog-item")
    fun createCatalogItem(@RequestBody catalogItemEntity: CatalogItemEntity): Long {
         return catalogItemFacade.createCatalogItem(catalogItemEntity)
    }

    @PostMapping("/save-catalog-item-photo")
    fun saveCatalogItemPhoto(@ModelAttribute photo: MultipartFile): String {
        return photoUploadingDAL.saveCatalogItemPhoto(photo)
    }
}
