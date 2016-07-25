package com.theironyard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringAwwCrudApplication.class)
@WebAppConfiguration
public class SpringAwwCrudApplicationTests {

	@Autowired
	WebApplicationContext wap;

	@Autowired
	CarRepository cars;

	@Autowired
	UserRepository users;

	MockMvc mockMvc;

	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/login")
					.param("userName", "testUserName")
					.param("password", "testUserPassword")
		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		assertTrue(users.count() == 1);
	}

	@Test
	public void testLogout() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/logout")
		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@Test
	public void testCreateCar() throws Exception {
		User testUser = new User("testUser", "password");
		users.save(testUser);
		String testDrivetrain = "FR";
		String testMake = "Porsche";
		String testModel = "Cayman";
		int year = 2005;
		Car testCar = new Car(testDrivetrain, testMake, testModel, year, testUser);
		cars.save(testCar);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-car")
				.param("year", "2005")
				.param("make", "testyear")
				.param("model", "testModel")
				.param("drivetrain", "testDrivetrain")
		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
		Car checkCar = cars.findOne(testCar.getId());
		assertEquals("drivetrain not created", testDrivetrain, testCar.getDrivetrain());
		assertEquals("id not equal", testCar.getId(), checkCar.getId());
		assertEquals("make not created", testMake, checkCar.getMake());
		assertEquals("model not created", testModel, checkCar.getModel());
		assertEquals("year not created", 2005, checkCar.getYear());
	}

	@Test
	public void testEdit() throws Exception {
		User testUser = new User("testUser", "password");
		users.save(testUser);
		String testDrivetrain = "FR";
		String testMake = "Porsche";
		String testModel = "Cayman";
		int year = 2005;
		Car testCar = new Car(testDrivetrain, testMake, testModel, year, testUser);
		cars.save(testCar);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/edit")
					.param("carId", "1")
					.param("drivetrain", "testDrivetrain")
					.param("make", "testMake")
					.param("model", "testModel")
					.param("year", "2005")
		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		assertTrue(cars.count() == 1);
		Car checkCar = cars.findOne(testCar.getId());
		assertEquals("drivetrain not changed", "testDrivetrain", checkCar.getDrivetrain());
		assertEquals("id not equal", 1, checkCar.getId());
		assertEquals("make not changed", "testMake", checkCar.getMake());
		assertEquals("model not changed", "testModel", checkCar.getModel());
		assertEquals("year not changed", 2005, checkCar.getYear());
	}

	@Test
	public void testDelete() throws Exception {
		User testUser = new User("testUser", "password");
		users.save(testUser);
		String testDrivetrain = "FR";
		String testMake = "Porsche";
		String testModel = "Cayman";
		int year = 2005;
		Car testCar = new Car(testDrivetrain, testMake, testModel, year, testUser);
		cars.save(testCar);
		String idString = String.valueOf(testCar.getId());
		mockMvc.perform(
				MockMvcRequestBuilders.post("/delete")
				.param("carId", idString)

		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

	}
}