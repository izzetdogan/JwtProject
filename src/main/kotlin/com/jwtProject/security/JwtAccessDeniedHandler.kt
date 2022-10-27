package com.jwtProject.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAccessDeniedHandler(): AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response?.contentType="application/json"
        response?.status= HttpServletResponse.SC_FORBIDDEN
        response?.outputStream?.println("You are not Authorized " + accessDeniedException?.message)
    }
}