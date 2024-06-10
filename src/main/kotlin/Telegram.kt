import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun main(args: Array<String>) {

    val botToken = args[0]
    var updateId = 0

    while (true) {

        Thread.sleep(1000)
        val updates = getUpdates(botToken, updateId)

        if (getUpdateId(updates) == null) continue
        else {
            println(updates)
            updateId = getUpdateId(updates)!!.toInt() + 1
        }

        println(getTextClient(updates))
    }
}

fun getUpdates(botToken: String, updateId: Int): String {

    val urlGetUpdates = "https://api.telegram.org/bot$botToken/getUpdates?offset=$updateId"
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder().uri(URI.create(urlGetUpdates)).build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    return response.body()
}

fun getTextClient(updates: String): String? {

    val messageTextRegex: Regex = "\"text\":\"(.+?)\"".toRegex()
    val matchResult = messageTextRegex.find(updates)
    val groups = matchResult?.groups

    return groups?.get(1)?.value
}

fun getChatId(updates: String): Int? {

    val messageTextRegex: Regex = "\"id\":(.+?),".toRegex()
    val matchResult = messageTextRegex.find(updates)
    val groups = matchResult?.groups

    return groups?.get(1)?.value?.toIntOrNull()
}

fun getUpdateId(updates: String): String? {

    val messageTextRegex: Regex = "\"update_id\":(.+?),".toRegex()
    val matchResult = messageTextRegex.find(updates)
    val groups = matchResult?.groups

    return groups?.get(1)?.value
}