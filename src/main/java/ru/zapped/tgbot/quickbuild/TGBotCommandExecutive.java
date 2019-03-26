package ru.zapped.tgbot.quickbuild;

import java.lang.reflect.Method;

public class TGBotCommandExecutive implements ITGBotCommandExecutive {

    private final TGBotCommandHandler FtgbotCommandHandler;

    TGBotCommandExecutive(TGBotCommandHandler tgBotCommandHandler) {
        super();
        FtgbotCommandHandler = tgBotCommandHandler;
    }

    private Method findCommandByName(String commandText) {
        for(Method method: FtgbotCommandHandler.getClass().getMethods()) {
            if (method.isAnnotationPresent(BotCommand.class)) {
                BotCommand command = method.getAnnotation(BotCommand.class);
                if (command.Command().equalsIgnoreCase(commandText)) {
                    return method;
                }
            }
        }
        try {
            return FtgbotCommandHandler.getClass().getMethod("unknownCommand", ITGBotCommand.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private Method findCommand(ITGBotCommand command) {
        return findCommandByName(command.name());
    }

    private Boolean executeCommand(Method commandMethod, ITGBotCommand command) {
        if (commandMethod == null)
            return false;
        try {
            commandMethod.invoke(FtgbotCommandHandler, command);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean run(ITGBotCommand extractedCommand) {
        if (extractedCommand == null)
            return false;
        Method method = findCommand(extractedCommand);
        return executeCommand(method, extractedCommand);
    }
}
