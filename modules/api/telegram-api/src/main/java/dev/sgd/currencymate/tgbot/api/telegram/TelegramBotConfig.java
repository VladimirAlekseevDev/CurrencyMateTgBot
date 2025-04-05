package dev.sgd.currencymate.tgbot.api.telegram;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramBotConfig {

    private static final String BOT_REGISTERED_MSG =
            """
            🚀 Currency Mate Telegram Bot successfully registered, let's get started! 🌟
            """;

    @Bean
    public CommandLineRunner commandLineRunner(CurrencyMateTelegramBot telegramBotApi) {
        return args -> {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBotApi);

            log.info(BOT_REGISTERED_MSG);
        };
    }

}