package ru.zapped.tgbot.quickbuild;

import org.cfg4j.source.context.environment.ImmutableEnvironment;

public class TGBotEnvironment extends ImmutableEnvironment {

    public TGBotEnvironment() {
        super("TGBOT");
    }

    @Override
    public String toString() {
        return "TGBotEnvironment{}";
    }
}
