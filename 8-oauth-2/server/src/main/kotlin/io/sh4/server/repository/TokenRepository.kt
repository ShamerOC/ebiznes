package io.sh4.server.repository

import io.sh4.server.model.authentication.Token
import io.sh4.server.model.user.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TokenRepository : CrudRepository<Token, Long> {

    fun findByToken(token: String): Token?

    fun findAllByUserAndExpiredIsFalseOrRevokedIsFalse(user: User): List<Token>
}

