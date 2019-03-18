package ru.zapped.tgbot.quickbuild;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

public class TGBotQuickbuildMain {
    public static void main(String[] args) {
        System.out.println("Starting the bot...");

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            String botName = System.getenv("TGBOT_QUICKBUILD_NAME");
            String botToken = System.getenv("TGBOT_QUICKBUILD_TOKEN");
            String botTGEndpointURL = System.getenv("TGBOT_ENDPOINT_URL");
            DefaultBotOptions options = new DefaultBotOptions();
            if (!StringUtils.isEmpty(botTGEndpointURL)) {
                options.setBaseUrl(botTGEndpointURL);
            }
            LongPollingBot bot = new TGBotQuickbuild(options, botName, botToken);
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
