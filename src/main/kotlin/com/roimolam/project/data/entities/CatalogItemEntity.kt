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

        @Column (name="amount_in_Stock", nullable=false)
        val amountInStock: Int,

        @SafeHtml
        @Column (name="description", nullable=false)
        val description: String,

        @Column (name="photo_id", nullable=false)
        val photoId: String,

        @Column(name="item_type", nullable=false)
        @Enumerated(EnumType.STRING)
        val itemType: ItemType,

        @SafeHtml
        @Column(name="brand", nullable=false)
        val brand: String,

        @Transient
        var photoBase64String: ByteArray?
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as CatalogItemEntity

                if (id != other.id) return false
                if (title != other.title) return false
                if (price != other.price) return false
                if (amountInStock != other.amountInStock) return false
                if (description != other.description) return false
                if (photoId != other.photoId) return false
                if (itemType != other.itemType) return false
                if (brand != other.brand) return false
                if (photoBase64String != null) {
                        if (other.photoBase64String == null) return false
                        if (!photoBase64String!!.contentEquals(other.photoBase64String!!)) return false
                } else if (other.photoBase64String != null) return false

                return true
        }

        override fun hashCode(): Int {
                var result = id.hashCode()
                result = 31 * result + title.hashCode()
                result = 31 * result + price.hashCode()
                result = 31 * result + amountInStock
                result = 31 * result + description.hashCode()
                result = 31 * result + photoId.hashCode()
                result = 31 * result + itemType.hashCode()
                result = 31 * result + brand.hashCode()
                result = 31 * result + (photoBase64String?.contentHashCode() ?: 0)
                return result
        }
}