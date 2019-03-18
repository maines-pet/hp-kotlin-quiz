package com.manalili.hpQuizKotlin.controller

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class GameMasterControllerTest(){
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `perform get request`() {
        this.mockMvc.perform(get("/game/welcome")).andDo(print()).andExpect(status().isOk)
    }

}