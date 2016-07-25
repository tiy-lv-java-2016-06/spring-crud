package com.theironyard;

import com.theironyard.controllers.SpringCrudController;
import com.theironyard.repositories.RestaurantRepository;
import com.theironyard.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringCrudApplication.class)
@WebAppConfiguration
public class SpringCrudApplicationTests {

	@Autowired
	WebApplicationContext wap;

	@Autowired
	RestaurantRepository restaurants;

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
				MockMvcRequestBuilders.post("/login").param("userName", "TestUser").param("password", "TestPassword")
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
	public void testCreateRestaurant() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.post("/create-restaurant").param("restaurantName", "TestName")
				.param("menuType", "TestMenu").param("restaurantAddress", "TestAddress").param("restaurantRating", "TestRating")
				.sessionAttr(SpringCrudController.SESSION_USERNAME, "TestUser")
		);

		assertTrue(restaurants.count() == 1);
	}

	@Test
	public void testDeleteRestaurant() throws Exception {
		int id = 1;
		mockMvc.perform(
				MockMvcRequestBuilders.post("/delete").sessionAttr("id", id).sessionAttr("restaurants", restaurants)
		);

		assertTrue(restaurants.count() == 0);
	}

}
