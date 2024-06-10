fun main(args: Array<String>) {

    val botToken = args[0]
    var updateId = 0

    val botService = TelegramBotService(botToken)

    while (true) {

        Thread.sleep(1000)
        val updates = botService.getUpdates(updateId)

        updateId = botService.getUpdateId(updates)?.toIntOrNull()?.plus(1) ?: continue
        println(updates)
        val chatId = botService.getChatId(updates)
        botService.sendMessage(chatId, "Привет")


        println(botService.getTextClient(updates))
    }
}