package com.roimolam.project.com.roimolam.project.controller

import com.roimolam.project.com.roimolam.project.models.CatalogItem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class CatalogController {

    @GetMapping("/get-product")
    fun getCatalogItem(): CatalogItem {
        return CatalogItem("Rayban", 300.00f, 20, "")
    }
}
