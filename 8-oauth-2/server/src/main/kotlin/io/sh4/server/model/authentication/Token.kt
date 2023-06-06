package io.sh4.server.model.authentication

import io.sh4.server.model.user.User
import jakarta.persistence.*

@Entity
@Table(name = "tokens")
class Token(

    @Column(unique = true)
    val token: String,

    val revoked: Boolean,

    val expired: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) {

    fun toRevoked(): Token = Token(
        token = token,
        revoked = true,
        expired = true,
        user = user,
        id = id
    )

}
