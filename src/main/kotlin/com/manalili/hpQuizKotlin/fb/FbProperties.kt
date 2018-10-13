package com.manalili.hpQuizKotlin.fb

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("fb")
class FbProperties {
    lateinit var accessToken: String
}