import org.example.LearnWordsTrainer
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets

const val CALLBACK_DATA_ANSWER_PREFIX = "answer_"

class TelegramBotService(
    val botToken: String,
    var updates: String = "",
    val client: HttpClient = HttpClient.newBuilder().build(),
) {
    var chatId: Int = 0

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
    fun getChatIdClient(): Int = regexChatId.find(updates)?.groups?.get(1)?.value!!.toInt()
    fun getData(): String? = regexData.find(updates)?.groups?.get(1)?.value

    fun sendMessage(message: String) {

        val encoded = URLEncoder.encode(
            message,
            StandardCharsets.UTF_8
        )

        val url = "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=$encoded"
        val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun sendHelloMessage() {

        val message = "Для вызова главного меню введите /start"

        val encoded = URLEncoder.encode(
            message,
            StandardCharsets.UTF_8
        )

        val url = "https://api.telegram.org/bot$botToken/sendMessage?chat_id=$chatId&text=$encoded"
        val request = HttpRequest.newBuilder().uri(URI.create(url)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun sendMenu(menu: String) {

        val url = "https://api.telegram.org/bot$botToken/sendMessage"

        val request = HttpRequest.newBuilder().uri(URI.create(url))
            .header("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(menu))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    fun learnWords(trainer: LearnWordsTrainer) {
        while (true) {

            Thread.sleep(2000)
            updates = getUpdates(updateId)

            updateId = getUpdateId()?.toIntOrNull()?.plus(1) ?: continue
            chatId = getChatIdClient()

            val question = trainer.getQuestion()
            if (question == null) {
                sendMessage("Все слова выучены")
                return
            } else sendMenu(learnWordsMenu(chatId, question))

            if (getData() == "btn_start") {
                sendMenu(startMenu(chatId))
                return
            }

            if (getData()?.toIntOrNull() == question.variance.indexOf(question.correctAnswer)) {
                sendMessage("Ответ верный")
                question.correctAnswer.quantityApprove += 1
            } else sendMessage("ответ не верный")
        }
    }
}