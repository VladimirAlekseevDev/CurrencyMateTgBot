package dev.sgd.currencymate.tgbot.adapter.currencymate;

import dev.sgd.currencymate.tgbot.adapter.currencymate.client.CurrencymateClient;
import dev.sgd.currencymate.tgbot.adapter.currencymate.model.ExchangeRateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdapterCurrencymate {

    private final CurrencymateClient client;

    public ExchangeRateResponse getExchangeRate(String fromCurrency, String toCurrency) {
        return client.getCurrentExchangeRate(fromCurrency, toCurrency);
    }

}