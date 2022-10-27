package com.jwtProject.controller

import com.jwtProject.dto.UserDto
import com.jwtProject.request.UserRequest
import com.jwtProject.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping()
    fun createUser(@RequestBody request: UserRequest ): ResponseEntity<String> {
        this.userService.createUser(request);
        return ResponseEntity.ok("ss")
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDto>>{
        return  ResponseEntity(this.userService.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/my-user")
    fun getMyUser(): String{
        return (SecurityContextHolder.getContext().authentication.principal as UserDetails).toString()
    }


}