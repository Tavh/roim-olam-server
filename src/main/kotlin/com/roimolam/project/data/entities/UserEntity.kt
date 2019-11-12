package com.roimolam.project.data.entities

import javax.persistence.*

@Entity
@Table(name="users")
data class UserEntity (
    @Id
    @GeneratedValue
    val id: Long,

    @Column(name="email", nullable=false)
    val email: String,

    @Column(name="password", nullable=false)
    val password: String
)