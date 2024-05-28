package team.springpsring.petpartner.domain.security.user.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import team.springpsring.petpartner.domain.security.user.details.CustomUserDetails
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = CustomUserDetails(
            loginId = "loginId",
            password = "password",
            email = "email",
            name = "name",
            authorities = Collections.singletonList(SimpleGrantedAuthority("ROLE_USER"))
        )
        return InMemoryUserDetailsManager(userDetails)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests {
            it.requestMatchers(
                "/swagger",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/api-docs",
                "/api-docs/**",
                "/v3/api-docs/**"
            )
                .permitAll()
                .requestMatchers(HttpMethod.GET)
                .permitAll()
                .anyRequest()
                .authenticated()
        }
//        http.formLogin {
//            it.loginPage("/login")
//                .permitAll()
//        }
//        http.logout {
//            it.logoutSuccessUrl("/")
//        }
        return http.build()
    }
}