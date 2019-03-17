package ru.zapped.tgbot.quickbuild;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TGBotQuickbuild extends TelegramLongPollingBot
{
    private String fbotName = null;
    private String fbotToken = null;

    public TGBotQuickbuild(String botName, String botToken) throws Exception {
        super();
        fbotName = botName;
        fbotToken = botToken;
        if (StringUtils.isEmpty(fbotName) || StringUtils.isEmpty(fbotToken)) {
            throw new Exception("Bot name and/or it's token is not set");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
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
