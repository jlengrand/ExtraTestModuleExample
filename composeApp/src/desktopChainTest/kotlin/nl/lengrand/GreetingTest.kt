package nl.lengrand

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import work.socialhub.kbsky.ATProtocolException

class GreetingTest {
    @Test
    fun greet() {

        assertEquals("Hello, you!", Greeting().greet())
    }

    @Test
    fun connect(){
        assertThrows<ATProtocolException> { Greeting().connect() }

    }
}