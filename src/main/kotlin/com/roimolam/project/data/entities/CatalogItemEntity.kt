package com.roimolam.project.data.entities

import com.roimolam.project.enums.ItemType
import org.hibernate.validator.constraints.SafeHtml
import javax.persistence.*

@Entity
@Table (name="catalog_items")
data class CatalogItemEntity(

        @Id
        @GeneratedValue
        val id: Long,

        @SafeHtml
        @Column (name="title", nullable=false)
        val title: String,

        @Column (name="price", nullable=false)
        val price: Float,

        @SafeHtml
        @Column (name="description", nullable=false)
        val description: String,

        @Column(name="item_type", nullable=false)
        @Enumerated(EnumType.STRING)
        val itemType: ItemType,

        @SafeHtml
        @Column(name="brand", nullable=false)
        val brand: String,

        @ManyToOne
        val photo: CatalogItemPhotoEntity
)