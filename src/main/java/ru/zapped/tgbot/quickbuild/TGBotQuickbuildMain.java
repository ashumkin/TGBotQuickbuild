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
        TGBotQuickbuildConfig config = new TGBotQuickbuildConfigImpl();
        System.out.println("Reading the config file " + config.getFile());
        config.read();

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            DefaultBotOptions options = new DefaultBotOptions();
            if (!StringUtils.isEmpty(config.botTGEndpointURL())) {
                options.setBaseUrl(config.botTGEndpointURL());
            }
            LongPollingBot bot = new TGBotQuickbuild(options, config.botName(), config.botToken());
            botsApi.registerBot(bot);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
