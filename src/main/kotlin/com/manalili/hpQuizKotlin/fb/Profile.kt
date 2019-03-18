package com.manalili.hpQuizKotlin.fb

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Profile(

        @JsonProperty("id")
        val id: String,

        @JsonProperty("first_name")
        val firstName: String,

        @JsonProperty("last_name")
        val lastName: String
)