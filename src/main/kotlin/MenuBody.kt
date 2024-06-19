import org.example.AnswerOptions

fun startMenu(chatId: Int): String {
    val menuBody = """
    {
        "chat_id": $chatId,
        "text": "Основное меню",
        "reply_markup": {
            "inline_keyboard": [
                [
                    {
                        "text": "Learn words",
                        "callback_data": "btnLearnWords"
                    }
                ],
                [
                    {
                        "text": "Statistic",
                        "callback_data": "btnStatistic"
                    }
                ]
            ]
        }
    }
""".trimIndent()
    return menuBody
}

fun learnWordsMenu(chatId: Int, question: AnswerOptions): String {
    val menuBody = """
        {
    "chat_id": $chatId,
    "text": "Найдите перевод слова: ${question.correctAnswer.original}",
    "reply_markup": {
        "inline_keyboard": [
            [
                {
                    "text": "Выход в меню",
                    "callback_data": "btn_start"
                }
            ],
            [
                {
                    "text": "${question.variance[0].translate}",
                    "callback_data": "0"
                },
                {
                    "text": "${question.variance[1].translate}",
                    "callback_data": "1"
                }
            ],
            [
                {
                    "text": "${question.variance[2].translate}",
                    "callback_data": "2"
                },
                {
                    "text": "${question.variance[3].translate}",
                    "callback_data": "3"
                }
            ]
        ]
    }
}
    """.trimIndent()
    return menuBody
}