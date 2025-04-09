package dev.sgd.currencymate.tgbot.service.handler.command;

import dev.sgd.currencymate.tgbot.adapter.currencymate.AdapterCurrencymate;
import dev.sgd.currencymate.tgbot.domain.Command;
import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import dev.sgd.currencymate.tgbot.service.utils.FormattingUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetExchangeRatesCommandHandler implements CommandHandler {

    private static final String text =
            """   
            Привет, друг! 👋✨
            Лови свежие курсы валют и крипты, чтобы быть в теме! 💸💹
            
            📈💎 Крипта:
            
            💫 ₿ BTC → USD:  %s $
            💫 Ξ ETH - USD:  %s $
            
            💰🌍 Фиатные валюты:
            
            💵 USD → RUB: %s ₽
            💶 EUR → RUB: %s ₽
            
            💵 USD → TRY: %s ₺
            💶 EUR → TRY: %s ₺
            
            💵 USD → GEL: %s ₾
            💶 EUR → GEL: %s ₾
            
            💵 USD → THB: %s ฿
            💶 EUR → THB: %s ฿
            
            Желаю тебе крутого дня, отличного настроения и чтобы всё было на позитиве, а финансы — в порядке 🌈🚀
            Ты молодец, что следишь за курсами! 😎✨
            
            📲 Используй команду /get_exchange_rates чтобы получить актуальтую информацию о курсах!
            """;

    private final AdapterCurrencymate adapterCurrencymate;

    @Override
    public void handle(Update update, CurrencyMateTelegramBot telegramBot) {
        SendMessage newMessage = new SendMessage();
        newMessage.setChatId(update.getMessage().getChatId());
        newMessage.setText(getFormattedText());
        // newMessage.setReplyMarkup(); TODO

        telegramBot.executeAsync(newMessage);
    }

    @Override
    public Command handlesCommand() {
        return Command.GET_EXCHANGE_RATES;
    }

    private String getFormattedText() {
        String btc = getExchangeRate("BTC", "USD");
        String eth = getExchangeRate("ETH", "USD");
        String usdRUB = getExchangeRate("USD", "RUB");
        String eurRUB = getExchangeRate("EUR", "RUB");
        String usdTRY = getExchangeRate("USD", "TRY");
        String eurTRY = getExchangeRate("EUR", "TRY");
        String usdGEL = getExchangeRate("USD", "GEL");
        String eurGEL = getExchangeRate("EUR", "GEL");
        String usdTHB = getExchangeRate("USD", "THB");
        String eurTHB = getExchangeRate("EUR", "THB");

        return String.format(
                text,
                btc, eth,
                usdRUB, eurRUB,
                usdTRY, eurTRY,
                usdGEL, eurGEL,
                usdTHB, eurTHB
        );
    }

    private String getExchangeRate(String from, String to) {
        return FormattingUtils.getFormatted(
                adapterCurrencymate.getExchangeRate(from, to).getRate()
        );
    }

}