package com.roimolam.project.controller

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.PhotoUploadStatusWrapper
import com.roimolam.project.logic.CatalogItemFacade
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.enums.UserType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin("*", allowCredentials = "true", allowedHeaders = ["*"],
        methods = [ RequestMethod.POST,
                    RequestMethod.GET,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.PUT,
                    RequestMethod.PATCH ],
        exposedHeaders = [ "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "errorMessage" ])
@RestController
@RequestMapping("/catalog")
class CatalogController (@Autowired val catalogItemFacade: CatalogItemFacade,
                         @Autowired val photoUploadingDAL: PhotoDAL) {

    @UserPermission(userType = UserType.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-catalog-item")
    fun createCatalogItem(@RequestBody catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
        return catalogItemFacade.createCatalogItem(catalogItemEntity)
    }

    @UserPermission(userType = UserType.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-catalog-item-photo")
    fun saveCatalogItemPhoto(@ModelAttribute photo: MultipartFile): PhotoUploadStatusWrapper {
        return photoUploadingDAL.saveCatalogItemPhoto(photo)
    }

    @GetMapping("/get-catalog-item/{id}")
    fun getCatalogItem(@PathVariable id: Long): CatalogItemEntity? {
        return catalogItemFacade.getCatalogItem(id)
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
