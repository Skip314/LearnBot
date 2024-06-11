import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import javax.sound.midi.MetaMessage

class TelegramBotService(
    val botToken: String,
    var updates: String,
    val client: HttpClient = HttpClient.newBuilder().build(),
) {
    private val regexUpdateId = "\"update_id\":(.+?),".toRegex()
    private val regexTextClient = "\"text\":\"(.+?)\"".toRegex()
    private val regexChatId = "\"chat\":\\{\"id\":(.+?),".toRegex()
    private val regexData = "\"data\":\"(.+?)\"".toRegex()

    fun getUpdates(updateId: Int): String {

        val url = "https://api.telegram.org/bot$botToken/getUpdates?offset=$updateId"
        val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    fun getUpdateId(): String? = regexUpdateId.find(updates)?.groups?.get(1)?.value
    fun getTextClient(): String? = regexTextClient.find(updates)?.groups?.get(1)?.value
    fun getChatId(): Int = regexChatId.find(updates)?.groups?.get(1)?.value!!.toInt()
    fun getData(): String? = regexData.find(updates)?.groups?.get(1)?.value

    fun sendMessage(chatId: Int, message: String) {

        val encoded = URLEncoder.encode(
            message,
            StandardCharsets.UTF_8
        )

        val url = "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=$encoded"
        val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun sendMenuStart(chatId: Int) {

        val url = "https://api.telegram.org/bot$botToken/sendMessage"

        val request = HttpRequest.newBuilder().uri(URI.create(url))
            .header("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(sendMenuBody(chatId)))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }
}