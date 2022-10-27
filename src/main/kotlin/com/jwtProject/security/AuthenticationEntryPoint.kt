package com.jwtProject.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationEntryPoint(): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.contentType="application/json"
        response?.status= HttpServletResponse.SC_UNAUTHORIZED
        response?.outputStream?.println("dsdd " + authException?.message)
    }
}