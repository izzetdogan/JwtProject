package com.jwtProject.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.jwtProject.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtFilter
    (private val tokenGenerator: TokenGenerator,
     private val userService: UserService
) : OncePerRequestFilter()
{

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenFromHeader(request)
        try {
            if(!token.isBlank()){
                println("token $token")
                val username = tokenGenerator.decodeJwt(token).subject
                val userDetails= userService.loadUserByUsername(username)
                val authToken = UsernamePasswordAuthenticationToken(userDetails,null,userDetails.authorities)
                authToken.details= WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication=authToken
            }

            filterChain.doFilter(request,response)

        }catch (e: Exception){
            val objectMapper = ObjectMapper()
            response.contentType="application/json"
            val errors: HashMap<String,String> = HashMap()
            errors["error"] = e.message.toString()
            response.status=HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write(objectMapper.writeValueAsString(errors))

        }


    }

    fun getTokenFromHeader(request: HttpServletRequest): String{
        val header:String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if(header !=null && header.startsWith("Bearer ") )
            return  header.substring(7)
        return ""
    }


}