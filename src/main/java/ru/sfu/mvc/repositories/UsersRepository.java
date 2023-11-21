package ru.sfu.mvc.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sfu.mvc.models.User;


/**
 * Репозиторий для взаимодействия с данными пользователей в базе данных.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

  /**
   * Поиск пользователя по его имени.
   *
   * @param username Имя пользователя.
   * @return Объект Optional, содержащий пользователя с указанным именем (если существует).
   */
  Optional<User> findByUsername(String username);
}