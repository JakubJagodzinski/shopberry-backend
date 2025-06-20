package com.example.shopberry.seeder;

import java.io.IOException;

public interface DataSeeder {

    String DATA_DIRECTORY = "data";

    void seed() throws IOException;

}
