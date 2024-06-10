fun main(args: Array<String>) {

    val botToken = args[0]
    var updateId = 0

    val botService = TelegramBotService(botToken)

    while (true) {

        Thread.sleep(1000)
        val updates = botService.getUpdates(updateId)

        if (botService.getUpdateId(updates) == null) continue
        else {
            println(updates)
            updateId = botService.getUpdateId(updates)!!.toInt() + 1
            val chatId = botService.getChatId(updates)
            botService.sendMessage(chatId, "Привет")
        }

        println(botService.getTextClient(updates))
    }
}