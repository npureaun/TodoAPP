package team.springpsring.petpartner.domain.security.user.details

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    private val loginId: String,
    private val password: String,
    private val name: String,
    private val email: String,
    private val authorities: Collection<GrantedAuthority>
):UserDetails {
    //userInfo
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
    override fun getPassword(): String =password
    override fun getUsername(): String =name
    fun getEmail():String=email
    fun loginId():String=loginId

    //userConnect
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}