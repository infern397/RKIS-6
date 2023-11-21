package ru.sfu.mvc.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sfu.mvc.models.User;
import ru.sfu.mvc.repositories.UsersRepository;
import ru.sfu.mvc.security.UserDetails;



/**
 * Сервис для загрузки информации о пользователе из базы данных для аутентификации Spring Security.
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  /**
   * Репозиторий для взаимодействия с данными пользователей в базе данных.
   */
  private final UsersRepository usersRepository;

  /**
   * Конструктор класса UserDetailsService.
   *
   * @param usersRepository Репозиторий для взаимодействия с данными пользователей.
   */
  @Autowired
  public UserDetailsService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  /**
   * Загрузка информации о пользователе по его имени для аутентификации.
   *
   * @param username Имя пользователя.
   * @return Детали пользователя (UserDetails).
   * @throws UsernameNotFoundException Выбрасывается, если пользователь не найден.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = usersRepository.findByUsername(username);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    return new UserDetails(user.get());
  }
}
