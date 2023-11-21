package ru.sfu.mvc.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер обработки ошибок, реализующий интерфейс ErrorController.
 */
@Controller
public class ErrorControllerImpl implements ErrorController {

  /**
   * Обработчик запроса для обработки ошибок.
   *
   * @param request Запрос HTTP.
   * @param model   Модель для передачи данных в представление.
   * @return Имя представления для обработки ошибок.
   */
  @RequestMapping("/error")
  public String handleError(HttpServletRequest request, Model model) {
    // Получение статуса ошибки из атрибутов запроса
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

    if (status != null) {
      int statusCode = Integer.parseInt(status.toString());
      // Обработка ошибки 403 (Forbidden)
      if (statusCode == 403) {
        return "error403";
      }
      // Передача статуса ошибки в модель для отображения
      model.addAttribute("status", Integer.toString(statusCode));
    }

    // Возврат имени представления для обработки общих ошибок
    return "error";
  }
}

