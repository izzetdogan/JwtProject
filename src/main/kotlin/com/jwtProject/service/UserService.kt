package com.jwtProject.service

import com.jwtProject.dto.UserDto
import com.jwtProject.model.User
import com.jwtProject.repository.UserRepository
import com.jwtProject.request.UserRequest
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Stream


@Service
class UserService(
    private val userRepository: UserRepository,
    @Lazy private val passwordEncoder: PasswordEncoder
    ) : UserDetailsService
{

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = findUserByEmail(username)
        val roles = Stream.of(user.role)
            .map { SimpleGrantedAuthority(it?.name ?: "NORMAL") }.toList()

        return org.springframework.security.core.userdetails.User(user.email,user.password,roles)
    }


    fun getAllUsers(): List<UserDto>{
        return userRepository.findAll().stream()
            .map { user: User -> UserDto.convertToUserDto(user) }.toList()

    }

    fun getMyUser(): String{
        val auth = SecurityContextHolder.getContext().authentication
        return auth.toString()
    }



    fun createUser(request: UserRequest):String{
        //val user: User =   User(request.email,request.firstname,request.lastname,request.password);
        val user = User(
            firstname = request.firstname,
            lastname = request.lastname,
            email = request.email,
            password = passwordEncoder.encode(request.password)
        )

        userRepository.save(user)
        return "OK"
    }

    fun getUserByEmail(email:String): User{
        return findUserByEmail(email)
    }


    private fun findUserByEmail(email:String): User {
        return userRepository.findByEmail(email)
    }


}


