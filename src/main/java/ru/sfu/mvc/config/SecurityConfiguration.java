package ru.sfu.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.sfu.mvc.services.UserDetailsService;

/**
 * Конфигурационный класс для настройки безопасности приложения.
 */
@Configuration
@ComponentScan("ru.sfu.mvc")
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /**
     * Сервис пользовательских данных, необходимый для аутентификации.
     */
    private final UserDetailsService shopUserDetailsService;

    /**
     * Конструктор класса SecurityConfiguration.
     *
     * @param shopUserDetailsService Сервис пользовательских данных.
     */
    @Autowired
    public SecurityConfiguration(UserDetailsService shopUserDetailsService) {
        this.shopUserDetailsService = shopUserDetailsService;
    }

    /**
     * Метод создания менеджера аутентификации.
     *
     * @param http Объект конфигурации безопасности.
     * @return Менеджер аутентификации.
     * @throws Exception Возникает при ошибке конфигурации.
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(shopUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return authenticationManagerBuilder.build();
    }

    /**
     * Метод создания объекта кодировщика пароля.
     *
     * @return Кодировщик пароля.
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Метод создания цепочки фильтров безопасности.
     *
     * @param http Объект конфигурации безопасности.
     * @return Цепочка фильтров безопасности.
     * @throws Exception Возникает при ошибке конфигурации.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/auth/login", "/error", "/auth/register", "/auth/process_reg", "*.css", "*.js").permitAll()
                    .requestMatchers("/furniture/*/edit").hasRole("ADMIN")
                    .requestMatchers("/furniture/add").hasRole("ADMIN")
                    .anyRequest().hasAnyRole("USER", "ADMIN")
                )
            .formLogin(formLogin ->
                    formLogin
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/process_login")
                        .defaultSuccessUrl("/furniture", true)
                        .failureUrl("/auth/login?error")
                )
            .logout(logout ->
                    logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"));
        return http.build();
    }
}
