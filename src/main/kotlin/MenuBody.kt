fun sendMenuBody(chatId: Int): String {
    val sendMenuBody = """
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
    return sendMenuBody
}