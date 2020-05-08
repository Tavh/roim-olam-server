package com.roimolam.project.data.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.roimolam.project.enums.UserType
import org.hibernate.validator.constraints.SafeHtml
import org.springframework.web.multipart.MultipartFile
import java.sql.Clob
import javax.persistence.*

@Entity
@Table(name="catalog_item_photo")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CatalogItemPhotoWrapper (

        @Lob
        val photo: ByteArray,

        @Id
        @GeneratedValue
        val id: Long = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CatalogItemPhotoWrapper

        if (!photo.contentEquals(other.photo)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photo.contentHashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}