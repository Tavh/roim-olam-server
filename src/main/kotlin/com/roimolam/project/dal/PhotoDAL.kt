package com.roimolam.project.dal


import com.roimolam.project.constants.*
import com.roimolam.project.data.PhotoUploadIdWrapper
import com.roimolam.project.data.entities.CatalogItemPhotoEntity
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
class PhotoDAL (@Autowired val env: Environment,
                @PersistenceContext val entityManager: EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun saveCatalogItemPhoto(catalogItemPhoto: CatalogItemPhotoEntity): PhotoUploadIdWrapper {
        compressBase64(catalogItemPhoto)
        entityManager.persist(catalogItemPhoto)
        return PhotoUploadIdWrapper(catalogItemPhoto.id)
    }

    private fun compressBase64(catalogItemPhoto: CatalogItemPhotoEntity) {
        println("Initial image size : ${catalogItemPhoto.photoBase64.size}")
        FileUtils.writeByteArrayToFile(File(UNCOMPRESSED_PHOTO_PATH), catalogItemPhoto.photoBase64)
        val input = File(UNCOMPRESSED_PHOTO_PATH)
        val image = ImageIO.read(input)
        val output = File(COMPRESSED_PHOTO_PATH)
        val out = FileOutputStream(output)
        val writer = ImageIO.getImageWritersByFormatName("jpg").next()
        val ios = ImageIO.createImageOutputStream(out)
        writer.output = ios
        val param = writer.defaultWriteParam
        if (param.canWriteCompressed()) {
            param.compressionMode = ImageWriteParam.MODE_EXPLICIT
            param.compressionQuality = 0.05f
        }

        writer.write(null, IIOImage(image, null, null), param)
        out.close()
        ios.close()
        writer.dispose()
        catalogItemPhoto.photoBase64 = output.readBytes()
        println("Compressed image size : ${catalogItemPhoto.photoBase64.size}")
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