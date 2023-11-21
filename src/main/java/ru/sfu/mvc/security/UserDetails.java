package ru.sfu.mvc.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.sfu.mvc.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;

/**
 * Реализация интерфейса UserDetails для предоставления информации о пользователе системы.
 */
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

  /**
   * Объект, представляющий сущность пользователя.
   */
  private final User shopUser;

  /**
   * Конструктор класса UserDetails.
   *
   * @param shopUser Объект, представляющий сущность пользователя.
   */
  public UserDetails(User shopUser) {
    this.shopUser = shopUser;
  }

  /**
   * Получение коллекции ролей пользователя.
   *
   * @return Коллекция ролей пользователя.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(shopUser.getRole()));
  }

  /**
   * Получение пароля пользователя.
   *
   * @return Пароль пользователя.
   */
  @Override
  public String getPassword() {
    return shopUser.getPassword();
  }

  /**
   * Получение имени пользователя.
   *
   * @return Имя пользователя.
   */
  @Override
  public String getUsername() {
    return shopUser.getUsername();
  }

  /**
   * Проверка, не истек ли срок действия учетной записи пользователя.
   *
   * @return Всегда возвращает true.
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * Проверка, не заблокирована ли учетная запись пользователя.
   *
   * @return Всегда возвращает true.
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * Проверка, не истек ли срок действия учетных данных пользователя.
   *
   * @return Всегда возвращает true.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * Проверка, включен ли аккаунт пользователя.
   *
   * @return Всегда возвращает true.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }

  /**
   * Получение объекта, представляющего сущность пользователя.
   *
   * @return Объект, представляющий пользователя.
   */
  public User getUser() {
    return shopUser;
  }
}

