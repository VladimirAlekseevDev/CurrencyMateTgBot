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

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetExchangeRateCommandHandler implements CommandHandler {

    private static final String text =
            """   
            Привет, друг! 👋✨
            Лови свежие курсы валют и крипты, чтобы быть в теме! 📊💹
            
            📈💎 Крипта:
        
            🪙 ₿ Биткоин (BTC) - $ Доллар (USD):  %s $
            🪙 Ξ Эфириум - $ Доллар (USD):  %s $
            
            💰🌍 Фиатные валюты:
            
            💵 $ Доллар (USD) - ₽ Рубль (RUB):  %s ₽
            💶 € Евро (EUR) - ₽ Рубль (RUB):  %s ₽
            
            💵 $ Доллар (USD) - ₺ Турецкая лира (TRY):  %s
            💶 € Евро (EUR) - ₺ Турецкая лира (TRY):  %s
            
            💵 $ Доллар (USD) - ₾ Грузинский лари (GEL):  %s
            💶 € Евро (EUR) - ₾ Грузинский лари (GEL):  %s
            
            Желаем тебе крутого дня, отличного настроения и только удачных финансовых решений! 🌈🚀
            Ты молодец, что следишь за курсами! 😎✨
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

        return String.format(text, btc, eth, usdRUB, eurRUB, usdTRY, eurTRY, usdGEL, eurGEL);
    }

    private String getExchangeRate(String from, String to) {
        BigDecimal rate = adapterCurrencymate.getExchangeRate(from, to).getRate();

        return FormattingUtils.getFormatted(rate);
    }

}