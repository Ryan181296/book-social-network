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
	void contextLoads(){
		String filePath = System.getProperty("user.home") + "/Downloads/data.json";
		List<Record> records = new ArrayList<>();

		for (int i = 1; i <= 2000000; i++) {
			log.info("record" + i);
			String name = "Name" + i;
			String address = "Address" + i;
			String description = "Description for record " + i;
			records.add(new Record(name, address, description));
		}

		Gson gson = new Gson();
		try (FileWriter writer = new FileWriter(filePath)) {
			gson.toJson(records, writer);
			System.out.println("File JSON đã được tạo tại: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
