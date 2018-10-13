package com.manalili.hpQuizKotlin.fb

class WelcomeGreeting(
    val greeting: MutableList<GreetingLocale>
)

class GreetingLocale(val locale: String,val text: String)