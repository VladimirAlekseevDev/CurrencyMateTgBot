package dev.sgd.currencymate.tgbot.domain;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

public interface CurrencyMateTelegramBot extends LongPollingBot {

    void executeAsync(SendMessage sendMessage);

}