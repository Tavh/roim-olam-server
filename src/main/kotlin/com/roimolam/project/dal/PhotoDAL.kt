package com.roimolam.project.dal


import com.roimolam.project.constants.DEFAULT_CATALOG_ITEM_PHOTO_PATH
import com.roimolam.project.constants.SPRING_MAIN_DIRECTORY_KEY
import com.roimolam.project.data.PhotoUploadIdWrapper
import com.roimolam.project.data.entities.CatalogItemPhotoEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.zip.Deflater
import javax.imageio.ImageIO
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
class PhotoDAL (@Autowired val env: Environment,
                @PersistenceContext val entityManager: EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun saveCatalogItemPhoto(catalogItemPhoto: CatalogItemPhotoEntity): PhotoUploadIdWrapper {
        val photoAsByteArray = catalogItemPhoto.photoBase64
        val baos = ByteArrayOutputStream()
        val dfl = Deflater()
        dfl.setLevel(Deflater.BEST_COMPRESSION)
        dfl.setInput(photoAsByteArray)
        val buffer = ByteArray(4 * 1024)
        while (!dfl.finished()) {
            val size = dfl.deflate(buffer)
            baos.write(buffer, 0, size)
        }
        baos.close()
        catalogItemPhoto.photoBase64 = baos.toByteArray()
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
        val file = File("${mainDir}${DEFAULT_CATALOG_ITEM_PHOTO_PATH}")

        val image: BufferedImage
        image = ImageIO.read(file)

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)

        return byteArrayOutputStream.toByteArray()
    }
}