package ru.sfu.mvc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sfu.mvc.models.User;
import ru.sfu.mvc.repositories.UsersRepository;

/**
 * Сервис для работы с пользователями, включая их регистрацию.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    /**
     * Ключ для определения роли администратора при регистрации.
     */
    @Value("${adminKey}")
    private String adminKey;

    /**
     * Репозиторий для взаимодействия с данными пользователей в базе данных.
     */
    private final UsersRepository usersRepository;

    /**
     * Кодировщик пароля для безопасного хранения паролей пользователей.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор класса UserService.
     *
     * @param usersRepository Репозиторий для взаимодействия с данными пользователей.
     * @param passwordEncoder Кодировщик пароля.
     */
    @Autowired
    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Проверка наличия пользователя с заданным именем в базе данных.
     *
     * @param username Имя пользователя.
     * @return true, если пользователь существует, в противном случае - false.
     */
    public boolean hasUsername(String username) {
        return usersRepository.findByUsername(username).isPresent();
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user Объект пользователя, подлежащий регистрации.
     */
    @Transactional
    public void register(User user) {

        // Хеширование пароля перед сохранением в базе данных
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Определение роли пользователя в зависимости от ключа
        if (user.getKey().equals(this.adminKey)) {
            user.setRole("ROLE_ADMIN");
        } else {
            user.setRole("ROLE_USER");
        }

        // Сохранение пользователя в базе данных
        usersRepository.save(user);
    }
}
