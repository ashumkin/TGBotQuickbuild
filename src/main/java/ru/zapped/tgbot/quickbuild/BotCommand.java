package ru.zapped.tgbot.quickbuild;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
@interface BotCommand {
    String Command();
    String Description();
}
