package ru.otus.spring.telegram.handlers.menu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.spring.service.MainMenuService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.telegram.BotState;
import ru.otus.spring.telegram.handlers.InputMessageHandler;

@Component
public class MainMenuHandler implements InputMessageHandler {
  private final ReplyMessagesService messagesService;
  private final MainMenuService mainMenuService;

  public MainMenuHandler(ReplyMessagesService messagesService, MainMenuService mainMenuService) {
    this.messagesService = messagesService;
    this.mainMenuService = mainMenuService;
  }

  @Override
  public SendMessage handle(Message message) {
    return mainMenuService.getMainMenuMessage(message.getChatId(), "Воспользуйтесь главным меню");
  }

  @Override
  public BotState getHandlerName() {
    return BotState.SHOW_MAIN_MENU;
  }


}
