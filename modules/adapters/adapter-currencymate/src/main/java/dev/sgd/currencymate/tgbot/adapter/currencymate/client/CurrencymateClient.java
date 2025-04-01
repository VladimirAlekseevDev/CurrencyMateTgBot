package dev.sgd.currencymate.tgbot.adapter.currencymate.client;

import dev.sgd.currencymate.tgbot.adapter.currencymate.config.CurrencymateClientConfig;
import dev.sgd.currencymate.tgbot.adapter.currencymate.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "currencymateClient",
    url = "${app.adapter.currencymate.url}",
    configuration = CurrencymateClientConfig.class
)
public interface CurrencymateClient {

    @GetMapping("/api/v1/exchangeRate/current")
    ExchangeRateResponse getCurrentExchangeRate(@RequestParam(name = "fromCurrency") String fromCurrency,
                                                @RequestParam(name = "toCurrency") String toCurrency);

}