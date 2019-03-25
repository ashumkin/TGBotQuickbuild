package ru.zapped.tgbot.quickbuild;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TGBotQuickbuild extends TelegramLongPollingBot
{
    private String fbotName = null;
    private String fbotToken = null;
    private ITGBotCommandExecutive commandExecutive;

    public TGBotQuickbuild(DefaultBotOptions options, String botName, String botToken) throws Exception {
        super(options);
        fbotName = botName;
        fbotToken = botToken;
        testNameAndToken();
        commandExecutive = new TGBotCommandExecutive(new TGBotCommandHandler(this));
    }

    private void testNameAndToken() throws Exception {
        if (StringUtils.isEmpty(fbotName) || StringUtils.isEmpty(fbotToken)) {
            throw new Exception("Bot name and/or it's token is not set");
        }
    }

    private void testForCommand(Update update) {
        ITGBotCommand extractedCommand = null;
        if (update.getMessage().isCommand()) {
            extractedCommand = BotCommandExtractor.parseMessage(update);
        }
        commandExecutive.run(extractedCommand);
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            testForCommand(update);
        }
    }

    @Override
    public String getBotUsername() {
        return fbotName;
    }

    @Override
    public String getBotToken() {
        return fbotToken;
    }


}
