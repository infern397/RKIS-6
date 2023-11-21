package ru.sfu.mvc.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.sfu.mvc.models.User;
import ru.sfu.mvc.services.UserService;
import ru.sfu.mvc.util.UserValidator;


/**
 * Контроллер, отвечающий за авторизацию и регистрацию пользователей.
 */
@RequestMapping("/auth")
@Controller
public class AuthController {

    /**
     * Валидатор пользовательских данных.
     */
    private final UserValidator userValidator;

    /**
     * Сервис пользователей.
     */
    private final UserService userService;

    /**
     * Конструктор класса AuthController.
     *
     * @param userValidator Валидатор пользовательских данных.
     * @param userService   Сервис пользователей.
     */
    @Autowired
    public AuthController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    /**
     * Обработчик GET-запроса для отображения страницы входа.
     *
     * @return Имя представления страницы входа.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Обработчик GET-запроса для отображения страницы регистрации.
     *
     * @param shopUser Объект пользователя, передаваемый в модель представления.
     * @return Имя представления страницы регистрации.
     */
    @GetMapping("/register")
    public String regPage(@ModelAttribute("user") User shopUser) {
        return "auth/registration";
    }

    /**
     * Обработчик POST-запроса для обработки данных регистрации пользователя.
     *
     * @param user          Объект пользователя, передаваемый в модель представления и подлежащий валидации.
     * @param bindingResult Результаты валидации.
     * @return Перенаправление на страницу входа в случае успешной регистрации, иначе возврат на страницу регистрации.
     */
    @PostMapping("/process_reg")
    public String doReg(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        } else {
            userService.register(user);
            return "redirect:/auth/login";
        }
    }
}

