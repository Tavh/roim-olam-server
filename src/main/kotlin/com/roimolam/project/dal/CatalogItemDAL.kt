package com.roimolam.project.dal

import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.enums.ItemType
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import com.roimolam.project.exceptions.ApplicationException
import javax.xml.catalog.Catalog

@Repository
class CatalogItemDAL (@PersistenceContext val entityManager:EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun createCatalogItem(catalogItem: CatalogItemEntity): CatalogItemIDWrapper {
        entityManager.persist(catalogItem)

        return CatalogItemIDWrapper(catalogItem.id)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getCatalogItem(id: Long): CatalogItemEntity {
        return entityManager.find(CatalogItemEntity::class.java, id)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getAllCatalogItems(): List<CatalogItemEntity> {
        val query = entityManager.createQuery("FROM CatalogItemEntity")

        return query.resultList as List<CatalogItemEntity>
    }
}