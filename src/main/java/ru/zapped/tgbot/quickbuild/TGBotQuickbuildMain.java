package ru.zapped.tgbot.quickbuild;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TGBotQuickbuildMain {
    public static void main(String[] args) {
        System.out.println("Starting the bot...");

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            String botName = System.getenv("TGBOT_QUICKBUILD_NAME");
            String botToken = System.getenv("TGBOT_QUICKBUILD_TOKEN");
            botsApi.registerBot(new TGBotQuickbuild(botName, botToken));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
