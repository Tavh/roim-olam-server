package com.roimolam.project.utils

import com.roimolam.project.constants.DEVELOPMENT_PROFILE_NAME
import com.roimolam.project.constants.SPRING_ACTIVE_PROFILES
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class ApplicationStatus(@Autowired final val env: Environment,
                  final var isDevProfileActive: Boolean) {

    init {
        val activeProfiles = env.getProperty(SPRING_ACTIVE_PROFILES)
        isDevProfileActive = activeProfiles?.contains(DEVELOPMENT_PROFILE_NAME) ?: false
    }
}