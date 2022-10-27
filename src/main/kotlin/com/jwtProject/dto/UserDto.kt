package com.jwtProject.dto

import com.jwtProject.model.Role
import com.jwtProject.model.User

data class UserDto(
    val id: Long?,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val role: Role?
){
    companion object
    {
        @JvmStatic
        fun convertToUserDto(from: User): UserDto{
            return UserDto(
                from.id,
                from.firstname,
                from.lastname,
                from.email,
                from.password,
                from.role
            )
        }
    }
}
