import org.example.LearnWordsTrainer

var updateId = 0

fun main(args: Array<String>) {

    val botToken = args[0]

    val botService = TelegramBotService(botToken)
    val trainer = LearnWordsTrainer()
    trainer.loadDictionary()

    while (true) {

        Thread.sleep(1000)
        botService.updates = botService.getUpdates(updateId)

        updateId = botService.getUpdateId()?.toIntOrNull()?.plus(1) ?: continue
        botService.chatId = botService.getChatIdClient()

        if (botService.getTextClient() == "\\u041f\\u0440\\u0438\\u0432\\u0435\\u0442") {
            botService.sendMessage("Привет")
            botService.sendHelloMessage()
        }

        if (botService.getTextClient() == "/start") botService.sendMenu(startMenu(botService.chatId))
        if (botService.getData() == "btn_start") botService.sendMenu(startMenu(botService.chatId))

        when (botService.getData()) {
            "btnLearnWords" -> learnWords2(args[0], trainer)

            "btnStatistic" -> {
                val statistic = trainer.getStatistics()

                botService.sendMessage(
                    "выучено ${statistic.learned} слов из ${statistic.quantityWords} | ${statistic.part}%"
                )
                botService.sendMenu(startMenu(botService.chatId))
            }
        }
    }
}

fun learnWords2(botToken: String, trainer: LearnWordsTrainer) {

    val questionService = TelegramBotService(botToken)

    while (true) {

        Thread.sleep(2000)
        questionService.updates = questionService.getUpdates(updateId)

        updateId = questionService.getUpdateId()?.toIntOrNull()?.plus(1) ?: continue
        questionService.chatId = questionService.getChatIdClient()

        val question = trainer.getQuestion()
        if (question == null) {
            questionService.sendMessage("Все слова выучены")
            return
        } else questionService.sendMenu(learnWordsMenu(questionService.chatId, question))

        if (questionService.getData() == "btn_start") {
            questionService.sendMenu(startMenu(questionService.chatId))
            return
        }

        if (questionService.getData()?.toIntOrNull() == question.variance.indexOf(question.correctAnswer)) {
            questionService.sendMessage("Ответ верный")
            question.correctAnswer.quantityApprove += 1
        } else when(questionService.getData()) {
            "1","2","3","0" -> questionService.sendMessage("ответ не верный")
        }
    }
}