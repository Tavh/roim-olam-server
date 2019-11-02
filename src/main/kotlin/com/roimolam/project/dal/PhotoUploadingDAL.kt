package com.roimolam.project.dal

import com.roimolam.project.data.PhotoFileNameWrapper
import com.roimolam.project.exceptions.ApplicationException
import com.roimolam.project.exceptions.ErrorType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@Repository
class PhotoUploadingDAL (@Autowired val env: Environment,
                         val catalogPhotoDirectory: String? = env.getProperty("CATALOG_PHOTO_DIRECTORY")) {


    fun saveCatalogItemPhoto(photo: MultipartFile): PhotoFileNameWrapper {
        val imageAsByteArray = photo.bytes
        val path = Paths.get(catalogPhotoDirectory + photo.originalFilename)

        if (isPhotoFileNameExists(photo.originalFilename)) {
            throw ApplicationException(ErrorType.ITEM_ALREADY_EXISTS, "The photo name you uploaded already " +
                    "exists, please choose a different one")
        }

        Files.write(path, imageAsByteArray)

        return PhotoFileNameWrapper(photo.originalFilename.toString())
    }

    fun isPhotoFileNameExists(photoFileName: String?): Boolean{
        return File(catalogPhotoDirectory, photoFileName).exists()
    }
}