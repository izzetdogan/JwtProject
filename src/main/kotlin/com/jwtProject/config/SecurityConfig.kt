package com.jwtProject.config

import com.jwtProject.security.AuthenticationEntryPoint
import com.jwtProject.security.JwtAccessDeniedHandler
import com.jwtProject.security.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    ) {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
   fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager{
       return config.authenticationManager
   }



    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain{
        return http
            .csrf()
            .disable()
            .authorizeRequests().antMatchers("/api/v1/auth/**").permitAll()
            .and()
            .authorizeRequests().antMatchers(HttpMethod.GET).hasAnyAuthority("ADMIN")
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().antMatchers( "/api/v1/auth/**")
        }
    }

}
