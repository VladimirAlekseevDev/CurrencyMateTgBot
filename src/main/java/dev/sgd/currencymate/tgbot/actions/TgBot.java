package dev.sgd.currencymate.tgbot.actions;

import dev.sgd.currencymate.tgbot.adapter.currencymate.AdapterCurrencymate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@Component
public class TgBot extends TelegramLongPollingBot {

    private static final String text =
    """   
    Привет, друг! 👋✨
    
    Лови свежие курсы валют и крипты, чтобы быть в теме! 📊💹
    Мы собрали всё самое важное, чтобы ты мог легко ориентироваться в мире финансов 📈
    
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

    private static final NumberFormat NUMBER_FORMAT;
    static {
        NUMBER_FORMAT = NumberFormat.getInstance(Locale.of("ru", "RU"));
        NUMBER_FORMAT.setMinimumFractionDigits(0);
        NUMBER_FORMAT.setMaximumFractionDigits(2);
        NUMBER_FORMAT.setGroupingUsed(true);
    }

    private final String botUsername;
    private final AdapterCurrencymate adapterCurrencymate;

    public TgBot(@Value("${bot.token}") String botToken,
                 @Value("${bot.username}") String botUsername,
                 AdapterCurrencymate adapterCurrencymate) {
        super(botToken);
        this.botUsername = botUsername;
        this.adapterCurrencymate = adapterCurrencymate;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage newMessage = new SendMessage();
            newMessage.setChatId(update.getMessage().getChatId());
            newMessage.setText(getFormattedText());
            try {
                execute(newMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    private String getFormattedText() {
        String btc = getVal("BTC", "USD");
        String eth = getVal("ETH", "USD");
        String usdRUB = getVal("USD", "RUB");
        String eurRUB = getVal("EUR", "RUB");
        String usdTRY = getVal("USD", "TRY");
        String eurTRY = getVal("EUR", "TRY");
        String usdGEL = getVal("USD", "GEL");
        String eurGEL = getVal("EUR", "GEL");
        return String.format(text, btc, eth, usdRUB, eurRUB, usdTRY, eurTRY, usdGEL, eurGEL);
    }

    private String getVal(String from, String to) {
        BigDecimal rate = adapterCurrencymate.getExchangeRate(from, to).getRate();
        rate = rate.setScale(2, RoundingMode.HALF_UP);

        return NUMBER_FORMAT.format(rate);
    }

}