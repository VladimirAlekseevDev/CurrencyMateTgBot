package dev.sgd.currencymate.tgbot.service.usecase;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import dev.sgd.currencymate.tgbot.service.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static java.lang.Boolean.FALSE;

@Component
@RequiredArgsConstructor
public class OnUpdateReceivedUseCase {

    private static final String UNKNOWN_COMMAND_TEXT =
            """
            К сожалению такой команды я пока не знаю 😿🫠
            """;


    private final List<MessageHandler> messageHandlers;

    @SneakyThrows
    public void process(Update update, CurrencyMateTelegramBot telegramBot) {
        if (!update.hasMessage()
                || FALSE.equals(update.getMessage().hasText())
                || update.getMessage().getText().isBlank()) {
            SendMessage newMessage = new SendMessage();
            newMessage.setChatId(update.getMessage().getChatId());
            newMessage.setText(UNKNOWN_COMMAND_TEXT);
            // newMessage.setReplyMarkup(); TODO

            telegramBot.executeAsync(newMessage);
            return;
        }

        MessageHandler messageHandler = messageHandlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst()
                .orElseThrow(() -> new TelegramApiException("Unknown command: " + update.getMessage().getText()));

        messageHandler.handle(update, telegramBot);
    }

}