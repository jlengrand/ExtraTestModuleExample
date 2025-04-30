package nl.lengrand

import kotlinx.serialization.Serializable
import work.socialhub.kbsky.BlueskyFactory
import work.socialhub.kbsky.api.entity.com.atproto.server.ServerCreateSessionRequest
import work.socialhub.kbsky.domain.Service

@Serializable
data class User (val name: String)

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${User("you").name}!"
    }

    fun connect(){
        val response = BlueskyFactory
            .instance(Service.BSKY_SOCIAL.uri)
            .server()
            .createSession(
                ServerCreateSessionRequest().also {
                    it.identifier = "test"
                    it.password = "tests"
                }
            )

        println(response.data.accessJwt)}
}