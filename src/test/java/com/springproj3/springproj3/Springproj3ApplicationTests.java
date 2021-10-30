package com.springproj3.springproj3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Springproj3ApplicationTests {

	@Test
	void testFireStore() throws ExecutionException, InterruptedException {

		User temp = new User("Famil Babayev", 21);

		UserService.saveUser(temp);

		User getTemp = UserService.getUserDetails("Famil Babayev");

		assertEquals(temp.getName(), getTemp.getName());
		assertEquals(temp.getAge(), getTemp.getAge());
	}

}
