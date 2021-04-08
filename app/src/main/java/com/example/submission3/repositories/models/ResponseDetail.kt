package com.example.submission3.repositories.models

data class ResponseDetail(
    val avatar_url: String,
    val company: String,
    val followers: Int,
    val following: Int,
    val location: String,
    val login: String,
    val name: String,
    val url: String,
    val public_repos: Int
)