package com.jwtProject.controller

import com.jwtProject.request.LoginRequest
import com.jwtProject.request.UserRequest
import com.jwtProject.service.AuthService
import com.jwtProject.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService,private val userService: UserService) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<HashMap<String,String>>{
        return ResponseEntity.ok(authService.login(request))
    }

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest): ResponseEntity<String>{
        return ResponseEntity.ok(userService.createUser(request))
    }

}