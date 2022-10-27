package com.jwtProject.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap


@Component
class TokenGenerator {

    private val tokenKey= "someTokenKey"

    private val expire: Long= 5 * 60 * 60

    private val issuer: String= "someIssuer"



    // Generate Tokens(access/refresh) with username credential

    fun generateToken(authenticate: Authentication):HashMap<String, String>{
        val username = (authenticate.principal as UserDetails).username

        val map = HashMap<String,String>()
        map["accessToken"] = generateAccessToken(username)
        map["refreshToken"] = generateRefreshToken(username)
        return map
    }

    fun generateAccessToken(username:String): String{
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expire*1000))
            .withIssuer(issuer)
            .sign(Algorithm.HMAC256(tokenKey))
    }

    fun generateRefreshToken(username:String): String{
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(Date(System.currentTimeMillis() + expire*3000))
            .withIssuer(issuer)
            .sign(Algorithm.HMAC256(tokenKey))
    }


    //method for decode generated token in Filter
    fun decodeJwt(token: String): DecodedJWT{
        val algorithm: Algorithm = Algorithm.HMAC256(tokenKey)
        val verifier= JWT.require(algorithm).build()
        try {
            return verifier.verify(token)
        }
        catch (b: JWTVerificationException ){
            throw RuntimeException(b)
        }
    }
}
