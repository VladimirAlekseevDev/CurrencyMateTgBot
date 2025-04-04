package dev.sgd.currencymate.tgbot.service.handler;

import dev.sgd.currencymate.tgbot.domain.Command;
import dev.sgd.currencymate.tgbot.domain.CurrencyMateTelegramBot;
import dev.sgd.currencymate.tgbot.service.handler.command.CommandHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Component
public class CommandMessageHandler implements MessageHandler {

    private static final Set<Command> COMMANDS = EnumSet.allOf(Command.class);
    private static final Set<String> COMMAND_CODES = COMMANDS.stream().
            map(Command::getCode)
            .collect(Collectors.toSet());

    private final Map<Command, CommandHandler> commandHandlers;

    public CommandMessageHandler(List<CommandHandler> commandHandlers) {
        this.commandHandlers = commandHandlers.stream()
                .collect(toMap(CommandHandler::handlesCommand, identity()));
    }


    @Override
    public void handle(Update update, CurrencyMateTelegramBot telegramBot) {
        Message message = update.getMessage();
        Command command = getCommandByCode(message.getText());

        CommandHandler commandHandler = commandHandlers.get(command);
        commandHandler.handle(update, telegramBot);
    }

    @Override
    public boolean canHandle(Update update) {
        Message message = update.getMessage();

        return message.isCommand()
                && message.getText().startsWith("/")
                && COMMAND_CODES.contains(message.getText());
    }

    @SneakyThrows
    private Command getCommandByCode(String commandCode) {
        return COMMANDS.stream()
                .filter(command -> command.getCode().equals(commandCode))
                .findFirst()
                .orElseThrow(() -> new TelegramApiException("Unknown command: " + commandCode));

    }

}