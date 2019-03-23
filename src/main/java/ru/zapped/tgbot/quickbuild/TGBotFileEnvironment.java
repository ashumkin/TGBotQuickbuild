package ru.zapped.tgbot.quickbuild;

import org.cfg4j.source.context.environment.ImmutableEnvironment;

public class TGBotFileEnvironment extends ImmutableEnvironment {

    public TGBotFileEnvironment(String file) {
        super(file);
    }

    @Override
    public String toString() {
        return "TGBotFileEnvironment{}";
    }
}
