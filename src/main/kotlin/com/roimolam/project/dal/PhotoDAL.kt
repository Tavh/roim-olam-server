package com.roimolam.project.dal

import com.roimolam.project.data.PhotoUploadStatusWrapper
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.enums.ErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


@Repository
class PhotoDAL (@Autowired val env: Environment,
                val catalogPhotoDirectory: String? = env.getProperty("CATALOG_PHOTO_DIRECTORY"),
                val defaultImagePath: String = catalogPhotoDirectory + "default.jpg") {

    fun saveCatalogItemPhoto(photo: MultipartFile): PhotoUploadStatusWrapper {
        val imageAsByteArray = photo.bytes
        val path = Paths.get(catalogPhotoDirectory + photo.originalFilename)

        if (isPhotoFileNameExists(photo.originalFilename)) {
            throw ApplicationException(ErrorType.ITEM_ALREADY_EXISTS,
                                       "A photo with the name '${photo.originalFilename}' already exists")
        }

        Files.write(path, imageAsByteArray)

        return PhotoUploadStatusWrapper("OK")
    }

    private fun isPhotoFileNameExists(photoFileName: String?): Boolean{
        return File(catalogPhotoDirectory, photoFileName!!).exists()
    }

    fun getCatalogItemPhoto(photoFileName: String): String {
        val path = catalogPhotoDirectory + photoFileName
        val file = File(path)

        var image: BufferedImage
        image = try {
            ImageIO.read(file)
        } catch(e: IOException) {
            val defaultImageFile = File(defaultImagePath)
            ImageIO.read(defaultImageFile)
        }

        val byteArrayOutputStream = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", byteArrayOutputStream)

        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
    }
}