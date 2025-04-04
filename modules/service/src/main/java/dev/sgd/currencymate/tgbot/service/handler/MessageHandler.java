package dev.sgd.currencymate.tgbot.service.handler;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {

    void handle(Update update, CurrencyMateTelegramBot telegramBot);

    boolean canHandle(Update update);

}