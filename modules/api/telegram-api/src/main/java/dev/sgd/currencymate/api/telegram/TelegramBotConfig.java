package dev.sgd.currencymate.api.telegram;

import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.TimeZone;

@Slf4j
@Configuration
public class TelegramBotConfig {

    @Bean
    public CommandLineRunner commandLineRunner(CurrencyMateTelegramBot telegramBotApi) {
        return args -> {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBotApi);

            log.info("Tg Bot Application Default Time Zone: {}", TimeZone.getDefault().getID());
        };
    }

}