package com.roimolam.project.dal

import com.roimolam.project.data.entities.UserEntity
import com.roimolam.project.enums.UserType
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import com.roimolam.project.exceptions.ApplicationException


@Repository
class UsersDAL (@PersistenceContext val entityManager:EntityManager) {

    @Transactional(propagation = Propagation.REQUIRED)
    @Throws(ApplicationException::class)
    fun createUser(user: UserEntity): Long {
        entityManager.persist(user)

        return user.id
    }

    @Transactional(propagation=Propagation.REQUIRED)
    fun getUser(email: String): UserEntity? {

        val query = entityManager.createQuery("FROM UserEntity user WHERE user.email=:email").setParameter("email", email)

        val results = query.resultList

        return if (results.isEmpty()) {
            null
        } else {
            query.singleResult as UserEntity
        }
    }
}