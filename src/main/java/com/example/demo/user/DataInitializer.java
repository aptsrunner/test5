package com.example.demo.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository repository) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return args -> {
            if (repository.count() == 0) {
                UserEntity user = new UserEntity();
                user.setUsername("user");
                user.setPassword(encoder.encode("password"));
                user.setRoles("USER");
                repository.save(user);
            }
        };
    }
}
