package com.roimolam.project.controller

import com.roimolam.project.annotations.UserPermission
import com.roimolam.project.dal.PhotoDAL
import com.roimolam.project.data.CatalogItemDeleteStatusWrapper
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.logic.CatalogItemLogicFacade
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.data.CatalogItemsWrapper
import com.roimolam.project.enums.ItemType
import com.roimolam.project.enums.UserType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@CrossOrigin("*", allowCredentials = "true", allowedHeaders = ["*"],
        methods = [ RequestMethod.POST,
                    RequestMethod.GET,
                    RequestMethod.HEAD,
                    RequestMethod.OPTIONS,
                    RequestMethod.PUT,
                    RequestMethod.PATCH,
                    RequestMethod.DELETE ],
        exposedHeaders = [ "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "errorMessage" ])
@RestController
@RequestMapping("/catalog")
class CatalogController (@Autowired val catalogItemFacade: CatalogItemLogicFacade,
                         @Autowired val photoUploadingDAL: PhotoDAL) {

    @UserPermission(userType = UserType.ADMIN)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create-catalog-item")
    fun createCatalogItem(@RequestBody catalogItemEntity: CatalogItemEntity): CatalogItemIDWrapper {
        return catalogItemFacade.createCatalogItem(catalogItemEntity)
    }

    @GetMapping("/get-catalog-item/{id}")
    fun getCatalogItem(@PathVariable id: Long): CatalogItemEntity? {
        return catalogItemFacade.getCatalogItem(id)
    }

    @GetMapping("/get-all-catalog-items")
    fun getAllCatalogItems(): List<CatalogItemEntity> {
        return catalogItemFacade.getAllCatalogItems()
    }

    @GetMapping("/get-catalog-items-by-type")
    fun getCatalogItemsByType(@RequestParam itemType: ItemType, @RequestParam(required = false) page: Int?): CatalogItemsWrapper {
        return catalogItemFacade.getCatalogItemsByType(itemType, page)
    }

    @GetMapping("/get-catalog-items-by-brand-and-type")
    fun getCatalogItemsByBrand(@RequestParam itemType: ItemType,
                               @RequestParam brand: String): List<CatalogItemEntity> {
        return catalogItemFacade.getCatalogItemsByBrand(itemType, brand)
    }

    @GetMapping("get-catalog-items-by-free-text")
    fun getCatalogItemsByFreeText(@RequestParam freeText: String): List<CatalogItemEntity> {
        return catalogItemFacade.getCatalogItemsByFreeText(freeText)
    }

    @DeleteMapping("/delete-catalog-item/{id}")
    fun deleteCatalogItem(@PathVariable id: Long): CatalogItemDeleteStatusWrapper {
        return catalogItemFacade.deleteCatalogItem(id)
    }
}
