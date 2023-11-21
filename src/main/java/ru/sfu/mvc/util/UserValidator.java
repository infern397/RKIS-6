package ru.sfu.mvc.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.sfu.mvc.models.User;
import ru.sfu.mvc.services.UserService;


/**
 * Валидатор для проверки пользовательских данных при регистрации.
 */
@Component
public class UserValidator implements Validator {

  /**
   * Сервис для работы с пользователями.
   */
  private final UserService userService;

  /**
   * Ключ для определения роли администратора при регистрации.
   */
  @Value("${adminKey}")
  private String adminKey;

  /**
   * Конструктор класса UserValidator.
   *
   * @param userService Сервис для работы с пользователями.
   */
  @Autowired
  public UserValidator(UserService userService) {
    this.userService = userService;
  }

  /**
   * Поддержка проверки объекта класса User.
   *
   * @param clazz Класс объекта.
   * @return true, если поддерживается, false в противном случае.
   */
  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.equals(clazz);
  }

  /**
   * Метод для проведения валидации объекта User.
   *
   * @param target Объект для валидации.
   * @param errors Объект для записи ошибок валидации.
   */
  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;

    // Проверка уникальности имени пользователя
    if (userService.hasUsername(user.getUsername())) {
      errors.rejectValue("username", "", "This username was already taken!");
    }

    // Проверка корректности ключа администратора
    if (!user.getKey().isEmpty() && !user.getKey().equals(adminKey)) {
      errors.rejectValue("key", "", "Incorrect admin key!");
    }
  }
}
