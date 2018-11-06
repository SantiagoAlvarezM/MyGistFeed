package com.example.stgoa.gistlib.auth.services

//TODO this object sucks, get rid of this
object GithubConfig {

    val GITHUB_CLIENT_ID = "de5b985f5398d9bed15b"

    val GITHUB_CLIENT_SECRET = "5e922754f1b613e557daa66ca4e70a2c06d6c428"

    val GITHUB_REQUEST_NOTE = "Token for app"

    val GITHUB_API_HEADERS = hashMapOf(
        "UserResponse-Agent:" to "MyGistFeed",
        "Accept:" to "application/vnd.github.v3+json"
    )

    val GITHUB_API_SCOPES = arrayOf("user", "gist")

    val GITHUB_BASE_URL = "https://api.github.com/"

    val AUTHORIZATION = "Authorization"

    val KEY_USERNAME = "username"

    val KEY_PASSWORD = "password"

    val KEY_BASIC_TOKEN = "basic_token"

    val KEY_OAUTH_TOKEN = "oauth_token"

    val KEY_AUTHORIZATION_RESPONSE = "authorization_response"

    val KEY_USER_RESPONSE = "user_response"

    val BASIC_TOKEN_PREFIX = "Basic "

    val OAUTH_TOKEN_PREFIX = "Bearer "

    val ERROR_UNAUTHORIZED = 401

    val ERROR_FORBIDDEN = 403

    val ERROR_NOTFOUND = 404

}