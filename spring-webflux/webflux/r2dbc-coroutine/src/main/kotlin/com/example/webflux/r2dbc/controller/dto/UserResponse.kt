package com.example.webflux.r2dbc.controller.dto

import com.example.webflux.r2dbc.common.Image
import com.example.webflux.r2dbc.common.User
import java.util.Optional

data class UserResponse(
    val id: String,
    val name: String,
    val age: Int,
    val followCount: Long,
    val image: Optional<ProfileImageResponse>
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                user.id,
                user.name,
                user.age,
                user.followCount,
                user.profileImage.map { image: Image ->
                    ProfileImageResponse(
                        image.id,
                        image.name,
                        image.url
                    )
                }
            )
        }
    }

}