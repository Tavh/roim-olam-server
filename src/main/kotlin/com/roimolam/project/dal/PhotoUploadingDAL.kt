package com.roimolam.project.dal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Repository
class PhotoUploadingDAL (@Autowired val env: Environment) {

    fun saveCatalogItemPhoto(photo: MultipartFile): String {
        val folder = env.getProperty("CATALOG_PHOTO_DIRECTORY")
        val imageAsByteArray = photo.bytes
        val path = Paths.get(folder + photo.originalFilename)
        Files.write(path, imageAsByteArray)

        return photo.originalFilename.toString()
    }
}