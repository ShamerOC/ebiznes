package io.sh4.models

data class DiscordWebSocketResponse(var op: Int?, var d: DiscordWebSocketResponseData?)

data class DiscordWebSocketResponseData(var content: String, var author: DiscordWebSocketResponseAuthor)

data class DiscordWebSocketResponseAuthor(var id: Long)
