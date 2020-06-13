package com.roimolam.project.data.entities

import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity
@Table(name="catalog_item_photo")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CatalogItemPhotoEntity (

        @Lob
        var photoBase64: ByteArray,

        @Id
        @GeneratedValue
        @Column(name = "photo_id")
        var id: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatalogItemPhotoEntity

        if (!photoBase64.contentEquals(other.photoBase64)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photoBase64.contentHashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}