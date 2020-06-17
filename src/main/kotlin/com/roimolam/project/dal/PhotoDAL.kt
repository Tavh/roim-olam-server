package com.roimolam.project.dal


import com.roimolam.project.constants.*
import com.roimolam.project.data.PhotoUploadIdWrapper
import com.roimolam.project.data.entities.CatalogItemPhotoEntity
import com.roimolam.project.utils.compressBytes
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
class PhotoDAL (@Autowired val env: Environment,
                @PersistenceContext val entityManager: EntityManager) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional(propagation = Propagation.REQUIRED)
    fun saveCatalogItemPhoto(catalogItemPhoto: CatalogItemPhotoEntity): PhotoUploadIdWrapper {
        val mainDir = System.getProperty(SPRING_MAIN_DIRECTORY_KEY)
        val tempDirPath = "${mainDir}$TEMP_COMPRESSION_FOLDER"
        val dir = File(tempDirPath)
        if (!dir.exists()) {
            if (dir.mkdir()) {
                logger.debug("Creating new directory: $mainDir$TEMP_COMPRESSION_FOLDER")
            } else {
                logger.debug("Failed to create directory: $mainDir$TEMP_COMPRESSION_FOLDER")
            }
        }
        val uncompressedFilePath = "$mainDir$UNCOMPRESSED_PHOTO_PATH"
        val compressedFilePath = "$mainDir$COMPRESSED_PHOTO_PATH"

        catalogItemPhoto.apply {
            val fileSizeGrade = this.photoBase64.size / PhotoDAL.KILOBYTES_IN_MEGABYTE.toFloat()
            val finalFileSizeGrade = Math.max(1f, fileSizeGrade)
            val compressionFactor = PhotoDAL.MAX_COMPRESSION_FACTOR - (finalFileSizeGrade * PhotoDAL.SINGLE_COMPRESSION_GRADE_VALUE)
            this.photoBase64 = compressBytes(this.photoBase64, logger, compressionFactor, uncompressedFilePath, compressedFilePath)
        }
        entityManager.persist(catalogItemPhoto)
        return PhotoUploadIdWrapper(catalogItemPhoto.id)
    }

    fun getCatalogItemPhoto(id: String): ByteArray {
        entityManager.find(CatalogItemPhotoEntity::class.java, id).apply {
            if (this != null) {
                return photoBase64
            }
        }

        val mainDir = System.getProperty(SPRING_MAIN_DIRECTORY_KEY)
        val file = File("$mainDir$DEFAULT_CATALOG_ITEM_PHOTO_PATH")

        val image: BufferedImage
        image = ImageIO.read(file)

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }

    companion object {
        const val MAX_COMPRESSION_FACTOR = 0.3f
        const val SINGLE_COMPRESSION_GRADE_VALUE = 0.02f
        const val KILOBYTES_IN_MEGABYTE = 1000000
    }
}