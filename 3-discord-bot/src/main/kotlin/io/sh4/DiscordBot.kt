package io.sh4

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_INVALID_SUBTYPE
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.consumeAsFlow

class DiscordBot(private val token: String) {

    private val client = HttpClient(CIO).config { install(WebSockets) }

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())
        .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(FAIL_ON_INVALID_SUBTYPE, false)

    private val LIST_CATEGORY_INTENTS = listOf("list category", "list categories")

    private val CATEGORY_MEAT = "Meet"

    private val CATEGORY_VEGAN = "Vegan"

    private val CATEGORIES = listOf(CATEGORY_MEAT, CATEGORY_VEGAN)

    private val PRODUCTS_FROM_CATEGORIES = mapOf(
        CATEGORY_MEAT to listOf("meat", "beef", "pork", "chicken", "fish"),
        CATEGORY_VEGAN to listOf("vegan", "vegetarian", "vegan food", "vegetarian food")
    )

    fun start() {
        runBlocking {
            client.webSocket(
                method = Get, host = "gateway.discord.gg", path = "/?v=10"
            ) {
                send(
                    """{
                                  "op": 2,
                                  "d": {
                                    "token": "$token",
                                    "intents": 512,
                                    "properties": {}
                                  }
                                }"""
                )
                while (true) {
                    incoming.consumeAsFlow().collect {
                        when (it) {
                            is Frame.Text -> handleTextFrame(it)
                            is Frame.Binary -> println(it.readBytes())
                            else -> println(it)
                        }
                    }
                }
            }
        }
    }

    private suspend fun handleTextFrame(frame: Frame.Text) {
        val response: Map<String, *> = objectMapper.readValue(frame.readText())
        println(response)
        println(response["d"])
        if (response["d"] != null) {
            val data = response["d"] as Map<*, *>
            if (data.containsKey("content") && data.containsKey("channel_id")) {
                handleMessage(data["content"] as String, data["channel_id"] as String)
            }
        }
    }

    private suspend fun handleMessage(message: String, channelId: String) {
        if (message.isOneOf(LIST_CATEGORY_INTENTS)) {
            var response = "Here are the available categories:\n"
            for (category in CATEGORIES) {
                response += category + "\n"
            }
            sendMessage(response, channelId)
        } else if (message.isOneOf(CATEGORIES)) {
            var response = "Here are the available products:\n"
            for (product in PRODUCTS_FROM_CATEGORIES[message]!!) {
                response += product + "\n"
            }
            sendMessage(response, channelId)
        }
    }

    private suspend fun sendMessage(message: String, channelId: String) {
        val body = """
                {
                    "content": "${message.replace("\n", " ")}"
                }
            """
        println(client.request("https://discord.com/api/v10/channels/$channelId/messages") {
            method = HttpMethod.Post
            header("Authorization", "Bot $token")
            contentType(ContentType.Application.Json)
            setBody(body)
        })
    }

    private fun String.isOneOf(words: List<String>): Boolean = words.any { this.contains(it) }
    
}


