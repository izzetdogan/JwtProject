package com.jwtProject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhotoAppApplication

fun main(args: Array<String>) {
	runApplication<PhotoAppApplication>(*args)
}
