package com.example.shopberry.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final List<DataSeeder> seeders;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (DataSeeder seeder : seeders) {
            seeder.seed();
        }
    }

}
