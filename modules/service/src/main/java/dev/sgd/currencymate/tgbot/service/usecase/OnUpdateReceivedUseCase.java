package dev.sgd.currencymate.tgbot.service.usecase;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import dev.sgd.currencymate.tgbot.service.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OnUpdateReceivedUseCase {

    private final List<MessageHandler> messageHandlers;

    @SneakyThrows
    public void process(Update update, CurrencyMateTelegramBot telegramBot) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return; // TODO
        }

        MessageHandler messageHandler = messageHandlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst()
                .orElseThrow(() -> new TelegramApiException("Unknown command: " + update.getMessage().getText()));

        messageHandler.handle(update, telegramBot);
    }

}