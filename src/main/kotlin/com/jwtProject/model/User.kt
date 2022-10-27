package com.jwtProject.model

import javax.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?=null,


    @Column(nullable = false, length = 50)
    val firstname: String,

    @Column(nullable = false, length = 50)
    val lastname: String,

    @Column(nullable = false, length = 50, unique = true)
    val email: String,

    @Column(nullable = false)
    val password : String,


    @Enumerated(EnumType.STRING)
    val role: Role? = Role.NORMAL


) {
    //constructor(_email: String, _firstname: String, _lastname: String, _password: String) : this(null,_firstname,_lastname,_email,_password,Role.NORMAL)

}


enum class Role{
    ADMIN,NORMAL
}
