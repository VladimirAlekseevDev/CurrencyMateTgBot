package dev.sgd.currencymate.tgbot.adapter.currencymate.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "dev.sgd.currencymate.tgbot.adapter.currencymate.client")
public class CurrencymateClientConfig {

    @Value("${app.adapter.currencymate.connectTimeoutMs}")
    private int connectTimeoutMillis;

    @Value("${app.adapter.currencymate.readTimeoutMs}")
    private int readTimeoutMillis;

    @Bean
    public Request.Options currencyMateOptions() {
        return new Request.Options(
                connectTimeoutMillis, TimeUnit.MILLISECONDS,
                readTimeoutMillis, TimeUnit.MILLISECONDS,
                true);
    }

}