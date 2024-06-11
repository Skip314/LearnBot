import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class TelegramBotService(
    val botToken: String,
    val client: HttpClient = HttpClient.newBuilder().build(),
) {
    private val regexUpdateId = "\"update_id\":(.+?),".toRegex()
    private val regexTextClient = "\"text\":\"(.+?)\"".toRegex()
    private val regexChatId = "\"id\":(.+?),".toRegex()

    fun getUpdates(updateId: Int): String {

        val urlGetUpdates = "https://api.telegram.org/bot$botToken/getUpdates?offset=$updateId"
        val request = HttpRequest.newBuilder().uri(URI.create(urlGetUpdates)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    fun getUpdateId(updates: String): String? {

        val messageTextRegex: Regex = regexUpdateId
        val matchResult = messageTextRegex.find(updates)
        val groups = matchResult?.groups

        return groups?.get(1)?.value
    }

    fun getTextClient(updates: String): String? {

        val messageTextRegex: Regex = regexTextClient
        val matchResult = messageTextRegex.find(updates)
        val groups = matchResult?.groups

        return groups?.get(1)?.value
    }

    fun getChatId(updates: String): Int {

        val messageTextRegex: Regex = regexChatId
        val matchResult = messageTextRegex.find(updates)
        val groups = matchResult?.groups

        return groups?.get(1)?.value!!.toInt()
    }

    fun sendMessage(chatId: Int, text: String) {

        val urlGetUpdates = "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=$text"
        val request = HttpRequest.newBuilder().uri(URI.create(urlGetUpdates)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }
}