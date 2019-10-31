package com.roimolam.project.models

import javax.persistence.*

@Entity
@Table (name="catalog_items")
data class CatalogItemEntity(

        @Id
        @GeneratedValue
        val id: Long,

        @Column (name="title", nullable=false)
        val title: String,

        @Column (name="price", nullable=false)
        val price: Float,

        @Column (name="amount_in_Stock", nullable=false)
        val amountInStock: Int,

        @Column (name="image", nullable = true)
        val image: String
)