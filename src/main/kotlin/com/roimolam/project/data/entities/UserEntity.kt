package com.roimolam.project.data.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.roimolam.project.enums.UserType
import javax.persistence.*

@Entity
@Table(name="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserEntity (
    @Id
    @GeneratedValue
    val id: Long,

    @Column(name="email", nullable=false)
    val email: String,

    @Column(name="password", nullable=false)
    var password: String?,

    @Column(name="user_type", nullable=false)
    @Enumerated(EnumType.STRING)
    val userType: UserType?
)