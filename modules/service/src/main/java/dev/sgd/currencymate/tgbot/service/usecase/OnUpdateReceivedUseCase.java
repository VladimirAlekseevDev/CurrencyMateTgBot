package dev.sgd.currencymate.tgbot.service.usecase;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import dev.sgd.currencymate.tgbot.service.handler.MessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static java.lang.Boolean.FALSE;

@Component
@RequiredArgsConstructor
public class OnUpdateReceivedUseCase {

    private static final String UNKNOWN_COMMAND_TEXT =
            """
            К сожалению такой команды я пока не знаю 😿🫠
            
            📋 Знаю такие команды:
            1️⃣ /get_exchange_rates - получить курсы валют и крипты 📈
            """;


    private final List<MessageHandler> messageHandlers;

    @SneakyThrows
    public void process(Update update, CurrencyMateTelegramBot telegramBot) {
        if (!update.hasMessage()
                || FALSE.equals(update.getMessage().hasText())
                || update.getMessage().getText().isBlank()) {

            sendUnknownCommandMessage(update, telegramBot);
            return;
        }

        MessageHandler messageHandler = messageHandlers.stream()
                .filter(handler -> handler.canHandle(update))
                .findFirst()
                .orElse(null);

        if (messageHandler == null) {
            sendUnknownCommandMessage(update, telegramBot);
            return;
        }

        messageHandler.handle(update, telegramBot);
    }

    private void sendUnknownCommandMessage(Update update, CurrencyMateTelegramBot telegramBot) {
        SendMessage newMessage = new SendMessage();
        newMessage.setText(UNKNOWN_COMMAND_TEXT);
        newMessage.setChatId(update.getMessage().getChatId());
        // newMessage.setReplyMarkup(); TODO

        telegramBot.executeAsync(newMessage);
    }

}