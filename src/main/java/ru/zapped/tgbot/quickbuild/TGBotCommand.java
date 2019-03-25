package ru.zapped.tgbot.quickbuild;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TGBotCommand implements ITGBotCommand {
    private final String Fname;
    private final String Farguments;
    private Update Fupdate;

    public TGBotCommand(String name, String arguments, Update update) {
        super();
        Fname = name;
        Farguments = arguments;
        Fupdate = update;
    }

    public String name() {
        return Fname;
    }

    @Override
    public Update update() {
        return Fupdate;
    }

    ;
}
