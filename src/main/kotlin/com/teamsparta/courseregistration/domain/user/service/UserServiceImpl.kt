package com.teamsparta.courseregistration.domain.user.service

import com.teamsparta.courseregistration.domain.exception.ModelNotFoundException
import com.teamsparta.courseregistration.domain.user.dto.*
import com.teamsparta.courseregistration.domain.user.exception.InvalidCredentialException
import com.teamsparta.courseregistration.domain.user.model.Profile
import com.teamsparta.courseregistration.domain.user.model.User
import com.teamsparta.courseregistration.domain.user.model.UserRole
import com.teamsparta.courseregistration.domain.user.model.toResponse
import com.teamsparta.courseregistration.domain.user.repository.UserRepository
import com.teamsparta.courseregistration.infra.security.jwt.JwtPlugin
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : UserService {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw ModelNotFoundException("User", null)

        if (user.role.name != request.role || !passwordEncoder.matches(request.password, user.password) ) {
            throw InvalidCredentialException()
        }

        return LoginResponse(
            accessToken = jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.name
            )
        )
    }

    override fun signUp(request: SignUpRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalStateException("Email is already in use")
        }

        return userRepository.save(
            User(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                profile = Profile(
                    nickname = request.nickname
                ),
                role = when (request.role) {
                    "STUDENT" -> UserRole.STUDENT
                    "TUTOR" -> UserRole.TUTOR
                    else -> throw IllegalArgumentException("Invalid role")
                }
            )
        ).toResponse()
    }

    override fun updateUserProfile(userId: Long, updateUserProfileRequest: UpdateUserProfileRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("User", userId)
        user.profile = Profile(
            nickname = updateUserProfileRequest.nickname
        )

        return userRepository.save(user).toResponse()
    }

}