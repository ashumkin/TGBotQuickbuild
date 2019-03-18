package ru.zapped.tgbot.quickbuild;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.swing.plaf.synth.SynthTextAreaUI;
import javax.xml.bind.Element;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target(ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@interface BotCommand {
    String Command();
    String Description();
}

public class TGBotQuickbuild extends TelegramLongPollingBot
{
    private String fbotName = null;
    private String fbotToken = null;

    public TGBotQuickbuild(String botName, String botToken) throws Exception {
        super();
        fbotName = botName;
        fbotToken = botToken;
        testNameAndToken();
    }

    public TGBotQuickbuild(DefaultBotOptions options, String botName, String botToken) throws Exception {
        super(options);
        fbotName = botName;
        fbotToken = botToken;
        testNameAndToken();
    }

    private void testNameAndToken() throws Exception {
        if (StringUtils.isEmpty(fbotName) || StringUtils.isEmpty(fbotToken)) {
            throw new Exception("Bot name and/or it's token is not set");
        }
    }

    @BotCommand(Command="/start", Description = "Starts interacting with the bot")
    public void onCommandStart(Update update) {
        SendMessage message = createReply(update)
            .setText(String.format("Bot started to talk to %s", update.getMessage().getFrom().toString()));
        sendMessage(message);
    }

    @BotCommand(Command = "/help", Description = "Shows this help")
    public void helpCommand(Update update) {
        SendMessage message = createReply(update)
                .setText(String.format("Usage:\n%s", getHelp()));
        sendMessage(message);
    }

    public void unknownCommand(Update update) {
        helpCommand(update);
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

    private void sendMessage(SendMessage message) {
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private SendMessage createReply(Update update) {
        return new SendMessage() // Create a SendMessage object with mandatory fields
                .setChatId(update.getMessage().getChatId());
    }

    private Method findCommand(String commandText) {
        for(Method method: this.getClass().getMethods()) {
            if (method.isAnnotationPresent(BotCommand.class)) {
                BotCommand command = method.getAnnotation(BotCommand.class);
                if (command.Command().equalsIgnoreCase(commandText)) {
                    return method;
                }
            }
        }
        try {
            return this.getClass().getMethod("unknownCommand", Update.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private void executeCommand(Method commandMethod, Update update) {
        try {
            commandMethod.invoke(this, update);
        } catch (Exception e) {

        }
    }

    private String extractCommand(String messageText) {
        String[] tokens = StringUtils.split(messageText, " ", 2);
        return tokens[0];
    }

    private void testForCommand(Update update) {
        String extractedCommand = null;
        if (update.getMessage().isCommand()) {
            extractedCommand = extractCommand(update.getMessage().getText());
        }
        Method commandMethod = findCommand(extractedCommand);
        executeCommand(commandMethod, update);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            testForCommand(update);
        }
    }

    @Override
    public String getBotUsername() {
        return fbotName;
    }

    @Override
    public String getBotToken() {
        return fbotToken;
    }


}
