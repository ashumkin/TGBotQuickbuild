package ru.zapped.tgbot.quickbuild;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class BotCommandExtractor {
    public static ITGBotCommand parseMessage(TelegramLongPollingBot bot,Update update) {
        String[] tokens = StringUtils.split(update.getMessage().getText(), " ", 2);

        if (!commandIsForThisBot(bot, tokens[0])) {
            return null;
        }

        String arguments = null;
        if (tokens.length > 1) {
            arguments = tokens[1];
        }
        return new TGBotCommand(tokens[0], arguments, update);
    }

    private static Boolean commandIsForThisBot(TelegramLongPollingBot bot, String token) {
        String[] botTokens = StringUtils.split(token, "@", 2);
        if (botTokens.length > 1) {
            if (!bot.getBotUsername().equalsIgnoreCase(botTokens[1])) {
                return false;
            }
        }
        return true;
    }
}
