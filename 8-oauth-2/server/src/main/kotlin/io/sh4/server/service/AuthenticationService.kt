package io.sh4.server.service

import io.sh4.server.controller.request.RegisterUserRequest
import io.sh4.server.controller.response.AuthenticationResponse
import io.sh4.server.controller.request.AuthenticationRequest
import io.sh4.server.model.authentication.Role
import io.sh4.server.model.authentication.Token
import io.sh4.server.model.user.User
import io.sh4.server.repository.TokenRepository
import io.sh4.server.repository.UserRepository
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
@Slf4j
class AuthenticationService(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun registerAndCreateNewUser(request: RegisterUserRequest): AuthenticationResponse {
        require(!userRepository.existsByEmailIgnoreCase(request.email)) { "User with given email already exists" }

        val user = User(
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = Role.USER
        )

        val savedUser = userRepository.save(user)
        val jwtToken = jwtService.generateToken(user)
        saveUserToken(savedUser, jwtToken)

        return AuthenticationResponse(jwtToken)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userRepository.findByEmailIgnoreCase(request.email)
            ?: run { throw UsernameNotFoundException("User with given email does not exist") }

        val jwtToken = jwtService.generateToken(user)
        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)
        return AuthenticationResponse(jwtToken)
    }

    private fun saveUserToken(user: User, jwtToken: String) =
        tokenRepository.save(
            Token(
                user = user,
                token = jwtToken,
                expired = false,
                revoked = false
            )
        )

    private fun revokeAllUserTokens(user: User) =
        tokenRepository.findAllByUserAndExpiredIsFalseOrRevokedIsFalse(user)
            .map { it.toRevoked() }
            .apply { tokenRepository.saveAll(this) }
}