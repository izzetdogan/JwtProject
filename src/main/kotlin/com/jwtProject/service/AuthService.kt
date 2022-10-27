package com.jwtProject.service

import com.jwtProject.model.User
import com.jwtProject.request.LoginRequest
import com.jwtProject.request.UserRequest
import com.jwtProject.security.TokenGenerator
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.RuntimeException


@Service
class AuthService(
    private val tokenGenerator: TokenGenerator,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: PasswordEncoder
) {
    fun login(request: LoginRequest): HashMap<String,String> {

        try {
            return tokenGenerator.generateToken(authenticateUser(request.email,request.password))
            //return SecurityContextHolder.getContext().authentication.toString()
        }
        catch (e: Exception){
            throw RuntimeException(e)
        }
    }


    fun register(userRequest: UserRequest): String{
        User(
            firstname = userRequest.firstname,
            lastname = userRequest.lastname,
            email = userRequest.email,
            password =passwordEncoder.encode(userRequest.password)
        )
        return "You have registered"
    }


    private  fun authenticateUser(email: String, password:String): Authentication{
        return  authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(email,password))
    }

}