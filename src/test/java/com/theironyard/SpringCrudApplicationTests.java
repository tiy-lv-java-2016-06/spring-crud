package com.theironyard;

import com.theironyard.controllers.BottleTrackerController;
import com.theironyard.services.BottleRepository;
import com.theironyard.services.UserRepository;
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

import java.time.LocalDateTime;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringCrudApplication.class)
@WebAppConfiguration
public class SpringCrudApplicationTests {

	@Autowired
    WebApplicationContext wap;

    @Autowired
    BottleRepository bottles;

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
                    .param("userName", "TestUserName")
                    .param("password", "TestPassword")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        assertTrue(users.count() == 1);
    }
    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/logout")
                .param("session", "TestSession")
        ).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void testCreateBottle() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/create-bottle")
                    .param("bottleName", "Test Name")
                    .param("bottleProducer", "Test Producer")
                    .param("bottleRegion", "Test Region")
                    .param("bottleVintage", "Test Vintage")
                    .param("bottleVariety", "Test Variety")
                    .param("bottleABV", "Test ABV")
                    .sessionAttr("userName", "TestUserName")
        );

        assertTrue(bottles.count() == 1);
    }

    @Test
    public void testDeleteBottle() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/delete-bottle")
                        .param("id", "Test Id")
                        .sessionAttr("userName", "TestUserName")
        );
    }
}

