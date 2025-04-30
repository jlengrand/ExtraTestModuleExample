package nl.lengrand

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, you!"
    }
}