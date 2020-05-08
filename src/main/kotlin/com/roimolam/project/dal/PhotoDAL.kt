package com.roimolam.project.dal


import com.roimolam.project.data.PhotoUploadIdWrapper
import com.roimolam.project.data.entities.CatalogItemPhotoWrapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@Repository
class PhotoDAL (@Autowired val env: Environment,
                @PersistenceContext val entityManager: EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun saveCatalogItemPhoto(photo: MultipartFile): PhotoUploadIdWrapper {
        val imageAsByteArray = photo.bytes

        val catalogItemPhoto = CatalogItemPhotoWrapper(imageAsByteArray)
        entityManager.persist(catalogItemPhoto)

        return PhotoUploadIdWrapper(catalogItemPhoto.id)
    }

    fun getCatalogItemPhoto(id: Long): String {
        return entityManager.find(CatalogItemPhotoWrapper::class.java, id).getPhoto()
    }
}