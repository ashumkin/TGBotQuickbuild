package ru.zapped.tgbot.quickbuild;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.Method;
import java.util.List;

public class TGBotCommandHandler {

    private AbsSender Fbot;

    public TGBotCommandHandler(AbsSender fbot) {
        super();
        Fbot = fbot;
    }

    private void sendMessage(SendMessage message) {
        try {
            Fbot.execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage createReply(ITGBotCommand command) {
        return new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(command.update().getMessage().getChatId());
    }

    @BotCommand(Command="/start", Description = "Starts interacting with the bot")
    public void onCommandStart(ITGBotCommand command) {
        SendMessage message = createReply(command)
                .setText(String.format("Bot started to talk to %s", command.update().getMessage().getFrom().toString()));
        sendMessage(message);
    }

    @BotCommand(Command = "/help", Description = "Shows this help")
    public void helpCommand(ITGBotCommand command) {
        SendMessage message = createReply(command)
                .setText(String.format("Usage:\n%s", getHelp()));
        sendMessage(message);
    }

    @BotCommand(Command = "/list", Description = "Get configuration list: /list PATTERN")
    public void listCommand(ITGBotCommand command) {
        QuickbuildClient quickbuildClient = new QuickbuildClient();
        List<QuickbuildConfig> configList = quickbuildClient.listConfigurations(command.arguments());
        SendMessage message = createReply(command)
                .setText(configList.toString());
        sendMessage(message);
    }

    public void unknownCommand(ITGBotCommand command) {
        helpCommand(command);
    }

    private String getHelp() {
        StringBuilder result = new StringBuilder();
        for(Method method: this.getClass().getMethods()) {
            if (method.isAnnotationPresent(BotCommand.class)) {
                BotCommand command = method.getAnnotation(BotCommand.class);
                result.append(command.Command());
                result.append(" ");
                result.append(command.Description());
                result.append("\n");
            }
        }
        return result.toString();
    }
}
