package dev.sgd.currencymate.tgbot.service;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageHandler {

    void handle(Message message, CurrencyMateTelegramBot telegramBot);

}