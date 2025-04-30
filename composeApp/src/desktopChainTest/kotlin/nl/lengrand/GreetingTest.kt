package nl.lengrand

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GreetingTest {
    @Test
    fun greet() {

        assertEquals("Hello, you!", Greeting().greet())
    }

}