package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Service
public class ParseQueryDataService {

  public String parseCallbackQueryType(CallbackQuery callbackQuery) {

    return callbackQuery.getData().split("\\|")[0];
  }

  public String parseOfferKeyToHighlightSubscribeQuery(CallbackQuery callbackQuery) {

    return callbackQuery.getData().split("\\|")[1];
  }
}
