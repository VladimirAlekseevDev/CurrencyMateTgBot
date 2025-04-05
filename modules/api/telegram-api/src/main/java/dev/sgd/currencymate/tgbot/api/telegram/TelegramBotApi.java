package dev.sgd.currencymate.tgbot.api.telegram;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import dev.sgd.currencymate.tgbot.service.usecase.OnUpdateReceivedUseCase;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBotApi extends TelegramLongPollingBot implements CurrencyMateTelegramBot {

    private final String botUsername;
    private final OnUpdateReceivedUseCase onUpdateReceivedUseCase;

    public TelegramBotApi(@Value("${bot.token}") String botToken,
                          @Value("${bot.username}") String botUsername,
                          OnUpdateReceivedUseCase onUpdateReceivedUseCase) {
        super(botToken);
        this.botUsername = botUsername;
        this.onUpdateReceivedUseCase = onUpdateReceivedUseCase;
    }

    @Override
    public void onUpdateReceived(Update update) {
        onUpdateReceivedUseCase.process(update, this);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    @SneakyThrows
    public void executeAsync(SendMessage sendMessage) {
        super.executeAsync(sendMessage);
    }

}