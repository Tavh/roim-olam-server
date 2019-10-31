package com.roimolam.project.controller

import com.roimolam.project.dal.CatalogItemDAL
import com.roimolam.project.models.CatalogItemEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/catalog")
class CatalogController (@Autowired val catalogItemDAL: CatalogItemDAL) {

    @PostMapping("/create-catalog-item")
    fun createCatalogItem(@RequestBody catalogItemEntity: CatalogItemEntity): Long {
         return catalogItemDAL.createCatalogItem(catalogItemEntity)
    }
}
