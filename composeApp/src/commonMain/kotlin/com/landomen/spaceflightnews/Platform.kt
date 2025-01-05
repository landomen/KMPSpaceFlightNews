package com.landomen.spaceflightnews

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

