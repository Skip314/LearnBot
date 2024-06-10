import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class TelegramBotService(
    val botToken: String
) {

    fun getUpdates(updateId: Int): String {

        val urlGetUpdates = "https://api.telegram.org/bot$botToken/getUpdates?offset=$updateId"
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create(urlGetUpdates)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    fun getUpdateId(updates: String): String? {

        val messageTextRegex: Regex = "\"update_id\":(.+?),".toRegex()
        val matchResult = messageTextRegex.find(updates)
        val groups = matchResult?.groups

        return groups?.get(1)?.value
    }

    fun getTextClient(updates: String): String? {

        val messageTextRegex: Regex = "\"text\":\"(.+?)\"".toRegex()
        val matchResult = messageTextRegex.find(updates)
        val groups = matchResult?.groups

        return groups?.get(1)?.value
    }

    fun getChatId(updates: String): Int {

        val messageTextRegex: Regex = "\"id\":(.+?),".toRegex()
        val matchResult = messageTextRegex.find(updates)
        val groups = matchResult?.groups

        return groups?.get(1)?.value!!.toInt()
    }

    fun sendMessage(chatId: Int, text: String) {

        val urlGetUpdates = "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=$text"
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create(urlGetUpdates)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }
}