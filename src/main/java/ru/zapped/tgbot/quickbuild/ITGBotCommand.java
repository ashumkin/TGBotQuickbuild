package ru.zapped.tgbot.quickbuild;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ITGBotCommand {
    String name();
    Update update();
}
