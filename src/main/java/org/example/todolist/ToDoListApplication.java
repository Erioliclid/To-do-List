package org.example.todolist;

import org.example.todolist.model.Role;
import org.example.todolist.model.User;
import org.example.todolist.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ToDoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoListApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                // Пароли ДОЛЖНЫ быть зашифрованы!
                User user = new User("user",
                        passwordEncoder.encode("1234"),  // ← ЗАШИФРОВАНО!
                        "user@example.com",
                        Role.USER);
                userRepository.save(user);

                User admin = new User("admin",
                        passwordEncoder.encode("12345"),  // ← ЗАШИФРОВАНО!
                        "admin@example.com",
                        Role.ADMIN);
                userRepository.save(admin);

                System.out.println("Пользователи созданы в БД с зашифрованными паролями");
            }
        };
    }
}