package com.roimolam.project.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table (name="catalog_items")
data class CatalogItemEntity(
        @Id
        @GeneratedValue
        val id: Long,

        val title: String,

        val price: Float,

        val quantityInStock: Int,

        val image: String) {

}