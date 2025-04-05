package dev.sgd.currencymate.tgbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        log.info("⌚ Currency Mate Telegram Bot Application Default Time Zone: {}", TimeZone.getDefault().getID());
    }

}
