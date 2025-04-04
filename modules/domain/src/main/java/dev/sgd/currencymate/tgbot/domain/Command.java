package dev.sgd.currencymate.tgbot.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Command {

    GET_EXCHANGE_RATES("/get_exchange_rates"),
    ;

    private final String code;

}