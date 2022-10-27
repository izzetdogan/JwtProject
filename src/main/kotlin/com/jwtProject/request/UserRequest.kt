package com.jwtProject.request



data class UserRequest(
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
)