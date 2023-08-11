package com.cradlesoft.medreminder

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform