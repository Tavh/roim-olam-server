package com.roimolam.project.dal

import com.roimolam.project.data.entities.UserEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class UsersDAL (
        @PersistenceContext(unitName="CouponSpringUnit")
        val entityManager: EntityManager
) {

    @Transactional(propagation=Propagation.REQUIRED)
    fun getUser(email: String): UserEntity? {

        val query = entityManager.createQuery("FROM UserEntity user WHERE user.email=:email").setParameter("email", email)

        val user: UserEntity?

        val results = query.getResultList()

        if (results.isEmpty()) {
            user = null
        } else {
            user = query.getSingleResult() as UserEntity
        }

        return user
    }
}