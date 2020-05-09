package com.roimolam.project.dal


import com.roimolam.project.constants.DEFAULT_CATALOG_ITEM_PHOTO_PATH
import com.roimolam.project.constants.SPRING_MAIN_DIRECTORY_KEY
import com.roimolam.project.data.PhotoUploadStatusWrapper
import com.roimolam.project.data.entities.CatalogItemPhotoWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
class PhotoDAL (@Autowired val env: Environment,
                @PersistenceContext val entityManager: EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun saveCatalogItemPhoto(photo: MultipartFile, photoId: String): PhotoUploadStatusWrapper {
        val imageAsByteArray = photo.bytes

        val catalogItemPhoto = CatalogItemPhotoWrapper(imageAsByteArray, photoId)
        entityManager.persist(catalogItemPhoto)

        return PhotoUploadStatusWrapper("OK")
    }

    fun getCatalogItemPhoto(id: String): ByteArray {

        entityManager.find(CatalogItemPhotoWrapper::class.java, id).apply {
            if (this != null) {
                return photo
            }
        }

        val mainDir = System.getProperty(SPRING_MAIN_DIRECTORY_KEY)
        val file = File("${mainDir}${DEFAULT_CATALOG_ITEM_PHOTO_PATH}")

        val image: BufferedImage
        image = ImageIO.read(file)

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }
}