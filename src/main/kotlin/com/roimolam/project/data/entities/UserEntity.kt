package com.roimolam.project.data.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.roimolam.project.enums.UserType
import org.hibernate.validator.constraints.SafeHtml
import javax.persistence.*

@Entity
@Table(name="users")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserEntity (
    @Id
    @GeneratedValue
    val id: Long,

    @SafeHtml
    @Column(name="email", nullable=false)
    val email: String,

    @SafeHtml
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name="password", nullable=false)
    var password: String,

    @Column(name="user_type", nullable=false)
    @Enumerated(EnumType.STRING)
    val userType: UserType?
)