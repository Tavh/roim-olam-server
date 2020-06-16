package com.roimolam.project.dal


import com.roimolam.project.constants.COMPRESSED_PHOTO_PATH
import com.roimolam.project.constants.DEFAULT_CATALOG_ITEM_PHOTO_PATH
import com.roimolam.project.constants.SPRING_MAIN_DIRECTORY_KEY
import com.roimolam.project.constants.UNCOMPRESSED_PHOTO_PATH
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
import java.io.FileOutputStream
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
        val mainDir = System.getProperty(SPRING_MAIN_DIRECTORY_KEY)
        val uncompressedPhotoPath = "${mainDir}${UNCOMPRESSED_PHOTO_PATH}"
        val compressedPhotoPath = "${mainDir}${COMPRESSED_PHOTO_PATH}"
        FileOutputStream(uncompressedPhotoPath).use { stream -> stream.write(catalogItemPhoto.photoBase64) }
        val input = File(uncompressedPhotoPath)
        val image = ImageIO.read(input)
        val output = File(compressedPhotoPath)
        val out = FileOutputStream(output)
        val writer = ImageIO.getImageWritersByFormatName("jpg").next()
        val ios = ImageIO.createImageOutputStream(out)
        writer.output = ios
        val param = writer.defaultWriteParam
        if (param.canWriteCompressed()) {
            param.compressionMode = ImageWriteParam.MODE_EXPLICIT
            val fileSizeGrade = catalogItemPhoto.photoBase64.size / KILOBYTES_IN_MEGABYTE.toFloat()
            val finalFileSizeGrade = Math.max(1f, fileSizeGrade)
            val compressionFactor = MAX_COMPRESSION_FACTOR - (finalFileSizeGrade * SINGLE_COMPRESSION_GRADE_VALUE)
            println("Compression factor : $compressionFactor")
            param.compressionQuality = compressionFactor
        }
        writer.write(null, IIOImage(image, null, null), param)
        out.close()
        ios.close()
        writer.dispose()
        catalogItemPhoto.photoBase64 = output.readBytes()
        input.delete()
        output.delete()
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

    companion object {
        const val MAX_COMPRESSION_FACTOR = 0.3f
        const val SINGLE_COMPRESSION_GRADE_VALUE = 0.02f
        const val KILOBYTES_IN_MEGABYTE = 1000000
    }
}