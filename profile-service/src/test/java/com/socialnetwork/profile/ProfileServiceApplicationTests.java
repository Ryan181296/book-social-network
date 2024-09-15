package com.socialnetwork.profile;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProfileServiceApplicationTests {
	@Autowired
	@Qualifier("Bird")
	IAnimal iAnimal;

	@Test
	void contextLoads() {
		iAnimal.fly();
	}
}
