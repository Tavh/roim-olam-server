package com.roimolam.project.dal

import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths

@Repository
class PhotoUploadingDAL {

    fun saveCatalogItemPhoto(photo: MultipartFile): String {
        val folder = "D:\\Repositories\\Git\\roim-olam-server\\src\\main\\resources\\catalog_item_images\\"
        val imageAsByteArray = photo.bytes
        val path = Paths.get(folder + photo.originalFilename)
        Files.write(path, imageAsByteArray)

        return photo.originalFilename.toString()
    }


}