package com.roimolam.project.dal

import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.enums.ErrorType
import com.roimolam.project.enums.ItemType
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import com.roimolam.project.exceptions.ApplicationException
import org.springframework.beans.factory.annotation.Autowired

@Repository
class CatalogItemDAL (@PersistenceContext val entityManager:EntityManager,
                      @Autowired val photoDAL: PhotoDAL) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun createCatalogItem(catalogItem: CatalogItemEntity): CatalogItemIDWrapper {
        entityManager.persist(catalogItem)

        return CatalogItemIDWrapper(catalogItem.id)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getCatalogItem(id: Long): CatalogItemEntity {
        return entityManager.find(CatalogItemEntity::class.java, id).apply {
            if (this == null) {
                throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find catalog item with id: $id")
            }

            photoBase64String = photoDAL.getCatalogItemPhoto(photoFileName)
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getAllCatalogItems(): List<CatalogItemEntity> {
        val query = entityManager.createQuery("FROM CatalogItemEntity")

        return (query.resultList as List<CatalogItemEntity>).apply {
            if (isEmpty()) {
                throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
            }

            forEach { c -> c.photoBase64String = photoDAL.getCatalogItemPhoto(c.photoFileName) }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getCatalogItemsByType(itemType: ItemType): List<CatalogItemEntity> {
        val query = entityManager.createQuery("FROM CatalogItemEntity c WHERE c.itemType=:itemType")
                                 .setParameter("itemType", itemType)

        return (query.resultList as List<CatalogItemEntity>).apply {
            if (isEmpty()) {
                throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
            }

            forEach { c -> c.photoBase64String = photoDAL.getCatalogItemPhoto(c.photoFileName) }
        }
    }
}