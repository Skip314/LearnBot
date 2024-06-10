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

        val startId = updates.lastIndexOf("update_id")
        val endId = updates.lastIndexOf(",\n\"message\"")
        if (startId == - 1 || endId == - 1) continue
        else println(updates)
        val updateIdString = updates.substring(startId + 11, endId)

        updateId = updateIdString.toInt() + 1
    }
}

fun getUpdates(botToken: String, updateId: Int): String {

    val urlGetUpdates = "https://api.telegram.org/bot$botToken/getUpdates?offset=$updateId"
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder().uri(URI.create(urlGetUpdates)).build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    return response.body()
}
