package com.roimolam.project.controller

import com.roimolam.project.models.CatalogItemEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class CatalogController {

    @GetMapping("/get-product")
    fun getCatalogItem(): CatalogItemEntity {
        return CatalogItemEntity("Rayban", 300.00f, 20, "")
    }
}
