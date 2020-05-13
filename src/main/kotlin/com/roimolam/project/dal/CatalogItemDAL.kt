package com.roimolam.project.dal

import com.roimolam.project.constants.CATALOG_ITEMS_PER_PAGE
import com.roimolam.project.data.CatalogItemDeleteStatusWrapper
import com.roimolam.project.data.CatalogItemIDWrapper
import com.roimolam.project.data.entities.CatalogItemEntity
import com.roimolam.project.data.CatalogItemsWrapper
import com.roimolam.project.enums.ErrorType
import com.roimolam.project.enums.ItemType
import com.roimolam.project.exceptions.ApplicationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PathVariable
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

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
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getAllCatalogItems(): List<CatalogItemEntity> {
        val query = entityManager.createQuery("FROM CatalogItemEntity")

        if (query.resultList.isEmpty()) {
            throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
        }

        return query.resultList as List<CatalogItemEntity>
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getCatalogItemsByType(itemType: ItemType, page: Int?): CatalogItemsWrapper {
        val query = entityManager.createQuery("FROM CatalogItemEntity c WHERE c.itemType=:itemType")
                                 .setParameter("itemType", itemType)

        if (page != null) {
            query.firstResult = (page - 1) * CATALOG_ITEMS_PER_PAGE
            query.maxResults = CATALOG_ITEMS_PER_PAGE
        }

        val catalogItems = query.resultList as List<CatalogItemEntity>

        if (catalogItems.isEmpty()) {
            throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any catalog items")
        }

        val catalogItemsCountQuery = entityManager.createQuery("SELECT count(*) FROM CatalogItemEntity c WHERE c.itemType=:itemType").setParameter("itemType", itemType)
        val totalPages = catalogItemsCountQuery.getSingleResult() as Long / CATALOG_ITEMS_PER_PAGE.toDouble()
        val totalPagesRoundedUp = Math.ceil(totalPages).toLong()
        println("totalPages is: ${totalPages}, rounding up to: ${totalPagesRoundedUp}")

        return CatalogItemsWrapper(totalPagesRoundedUp, catalogItems)
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun getCatalogItemsByBrand(itemType: ItemType, brand: String): List<CatalogItemEntity> {
        val query = entityManager.createQuery("FROM CatalogItemEntity c WHERE c.itemType=:itemType " +
                                                "AND c.brand=:brand").setParameter("itemType", itemType)
                                                                     .setParameter("brand", brand)

        if (query.resultList.isEmpty()) {
            throw ApplicationException(ErrorType.NO_DATA_FOUND, "Couldn't find any $itemType with brand $brand")
        }

        return query.resultList as List<CatalogItemEntity>
    }

    @Transactional(propagation = Propagation.REQUIRED)
    fun deleteCatalogItem(@PathVariable id: Long): CatalogItemDeleteStatusWrapper {
        println(id)
        entityManager.find(CatalogItemEntity::class.java, id).apply {
            if (this != null) {
                entityManager.remove(this)
            }
        }

        return CatalogItemDeleteStatusWrapper("OK")
    }
}