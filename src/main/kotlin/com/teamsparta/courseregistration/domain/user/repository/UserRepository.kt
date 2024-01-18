package com.teamsparta.courseregistration.domain.user.repository

import com.teamsparta.courseregistration.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?
}