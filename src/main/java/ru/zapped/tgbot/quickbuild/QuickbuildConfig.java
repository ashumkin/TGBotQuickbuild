package ru.zapped.tgbot.quickbuild;

public class QuickbuildConfig {
    private final String Fentity;

    public QuickbuildConfig(String entity) {
        super();
        Fentity = entity;
    }

    @Override
    public String toString() {
        return Fentity;
    }
}
