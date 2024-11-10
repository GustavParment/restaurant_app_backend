package com.gustav.restaurant_app_ea.security.jwt

class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
) {
}