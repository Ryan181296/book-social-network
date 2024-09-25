package com.socialnetwork.profile;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class ProfileServiceApplicationTests {
    @Test
    void contextLoads() {
        String filePath = System.getProperty("user.home") + "/Downloads/discount.json";
        List<Record> records = new ArrayList<>();

        for (int i = 1; i <= 1000000; i++) {
            log.info("record {}", i);
            records.add(Record.builder()
                    .name("Discount " + i)
                    .code("CODE" + i)
                    .isUsed(false)
                    .description("Celebrate the season with savings! Enjoy 25% off on all items, valid throughout the holiday season.")
                    .build());
        }

        var gson = new Gson();
        try (var writer = new FileWriter(filePath)) {
            gson.toJson(records, writer);
            log.info("File is created: {}", filePath);
        } catch (IOException ignored) {
        }
    }
}
