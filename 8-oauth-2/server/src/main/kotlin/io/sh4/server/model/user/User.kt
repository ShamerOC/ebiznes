package io.sh4.server.model.user

import io.sh4.server.model.authentication.Role
import io.sh4.server.model.authentication.Token
import jakarta.persistence.*
import lombok.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    val firstName: String,

    val lastName: String,

    @Column(unique = true)
    val email: String,

    private val password: String, // private due to issue with JVM and Kotlin: `same JVM signature` for getters

    @Enumerated(EnumType.STRING)
    val role: Role,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    val tokens: List<Token> = emptyList(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) : UserDetails {

    override fun getAuthorities() =
        setOf(SimpleGrantedAuthority(role.name))

    override fun getPassword() =
        password

    override fun getUsername() =
        email

    override fun isAccountNonExpired() =
        true

    override fun isAccountNonLocked() =
        true

    override fun isCredentialsNonExpired() =
        true

    override fun isEnabled() =
        true
}

