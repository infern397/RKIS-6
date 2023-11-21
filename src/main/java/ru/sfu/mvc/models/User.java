package ru.sfu.mvc.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Класс, представляющий сущность пользователя в системе.
 */
@Entity
@Table(name = "users")
@Data
public class User {

  /**
   * Уникальный идентификатор пользователя.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  /**
   * Имя пользователя.
   */
  @Column(name = "username")
  @NotEmpty
  @Size(min = 2, max = 100)
  private String username;

  /**
   * Пароль пользователя.
   */
  @NotEmpty
  @Column(name = "u_password")
  private String password;

  /**
   * Роль пользователя в системе.
   */
  @Column(name = "u_role")
  private String role;

  /**
   * Ключ пользователя, используемый для предоставления статуса администратора.
   */
  @Column(name = "key")
  private String key;
}
