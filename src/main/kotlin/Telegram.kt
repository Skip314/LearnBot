import org.example.LearnWordsTrainer

fun main(args: Array<String>) {

    val botToken = args[0]
    var updateId = 0

    val botService = TelegramBotService(botToken, "")
    val trainer = LearnWordsTrainer()
    trainer.loadDictionary()

    while (true) {

        Thread.sleep(1000)
        botService.updates = botService.getUpdates(updateId)

        updateId = botService.getUpdateId()?.toIntOrNull()?.plus(1) ?: continue
        println(botService.updates)
        val chatId = botService.getChatId()

        if (botService.getTextClient() == "\\u041f\\u0440\\u0438\\u0432\\u0435\\u0442")
            botService.sendMessage(chatId, "Привет")

        if (botService.getTextClient() == "/start") {
            botService.sendMenuStart(chatId)
            when (botService.getData()) {
                "btnLearnWords" -> {
                    botService.sendMessage(chatId, "В разработке")
                    botService.sendMenuStart(chatId)
                }

                "btnStatistic" -> {
                    val statistic = trainer.getStatistics()
                        botService.sendMessage(
                        chatId, "выучено ${statistic.learned} слов из ${statistic.quantityWords} | ${statistic.part}%"
                    )
                    botService.sendMenuStart(chatId)
                }
            }
        }
    }
}