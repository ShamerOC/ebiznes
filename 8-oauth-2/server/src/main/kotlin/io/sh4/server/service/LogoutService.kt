package io.sh4.server.service

import io.sh4.server.repository.TokenRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class LogoutService(
    private val tokenRepository: TokenRepository
) : LogoutHandler {
    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            return
        }
        val jwt = authHeader.substring(BEARER.length - 1) // subtract one because index starts from 0
        tokenRepository.findByToken(jwt)
            ?.apply { this.toRevoked() }
            ?.run {
                tokenRepository.save(this)
                SecurityContextHolder.clearContext()
            }
    }

    companion object {
        private const val BEARER = "Bearer "
    }
}
