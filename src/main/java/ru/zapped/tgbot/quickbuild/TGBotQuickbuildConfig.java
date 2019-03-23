package ru.zapped.tgbot.quickbuild;

public interface TGBotQuickbuildConfig {
    String getFile();
    String botToken();
    String botName();
    String botTGEndpointURL();

    void read();
}
