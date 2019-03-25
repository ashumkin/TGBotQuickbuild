package ru.zapped.tgbot.quickbuild;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandExtractor {
    public static ITGBotCommand parseMessage(Update update) {
        String[] tokens = StringUtils.split(update.getMessage().getText(), " ", 2);

        String arguments = null;
        if (tokens.length > 1) {
            arguments = tokens[1];
        }
        return new TGBotCommand(tokens[0], arguments, update);
    }
}
