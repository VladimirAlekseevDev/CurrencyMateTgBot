package dev.sgd.currencymate.tgbot.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FormattingUtils {

    private static final NumberFormat NUMBER_FORMAT;
    static {
        NUMBER_FORMAT = NumberFormat.getInstance(Locale.of("ru", "RU"));
        NUMBER_FORMAT.setMinimumFractionDigits(0);
        NUMBER_FORMAT.setMaximumFractionDigits(2);
        NUMBER_FORMAT.setGroupingUsed(true);
    }

    public static String getFormatted(BigDecimal value) {
        value = value.setScale(2, RoundingMode.HALF_UP);

        return NUMBER_FORMAT.format(value);
    }

}