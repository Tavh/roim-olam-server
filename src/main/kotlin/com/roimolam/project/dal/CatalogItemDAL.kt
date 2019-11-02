package com.roimolam.project.dal

import com.roimolam.project.models.CatalogItemEntity
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.nio.file.Files
import java.nio.file.Paths


@Repository
class CatalogItemDAL (@PersistenceContext val entityManager:EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun createCatalogItem(catalogItem: CatalogItemEntity): Long {
        entityManager.persist(catalogItem)

        return catalogItem.id
    }
}