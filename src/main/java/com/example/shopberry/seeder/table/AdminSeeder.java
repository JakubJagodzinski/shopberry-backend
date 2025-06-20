package com.example.shopberry.seeder.table;

import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.user.Role;
import com.example.shopberry.user.User;
import com.example.shopberry.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements DataSeeder {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final static String ADMIN_FIRST_NAME = "admin";
    private final static String ADMIN_LAST_NAME = "admin";
    private final static String ADMIN_EMAIL = "admin@shopberry.com";
    private final static String ADMIN_PASSWORD = "admin";


    @Override
    public void seed() throws IOException {
        if (!userRepository.existsByEmail(ADMIN_EMAIL)) {
            User admin = new User();

            admin.setEmail(ADMIN_EMAIL);
            admin.setFirstName(ADMIN_FIRST_NAME);
            admin.setLastName(ADMIN_LAST_NAME);
            admin.setPassword(passwordEncoder.encode(ADMIN_PASSWORD));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }

}
