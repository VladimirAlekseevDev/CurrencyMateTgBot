package dev.sgd.currencymate.tgbot.service.handler.command;

import dev.sgd.currencymate.tgbot.domain.Command;
import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {

    void handle(Update update, CurrencyMateTelegramBot telegramBot);

    Command handlesCommand();

}