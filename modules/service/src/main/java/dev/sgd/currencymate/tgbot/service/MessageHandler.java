package dev.sgd.currencymate.tgbot.service;

public interface MessageHandler {

    void handle(String message, TelegramBotCore telegramBotCore);

}