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
		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-car")
				.param("year", "2005")
				.param("make", "testyear")
				.param("model", "testModel")
				.param("drivetrain", "testdrivetrain")
		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());
	}

	@Test
	public void testEdit() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/edit")
					.param("carId", "1")
					.param("drivetrain", "4WD")
					.param("make", "testMake")
					.param("model", "testModel")
					.param("year", "testYear")
		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		assertTrue(users.count() == 1);
	}

	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/delete")
				.param("carId", "1")

		).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

		assertTrue(users.count() == 1);
	}
}