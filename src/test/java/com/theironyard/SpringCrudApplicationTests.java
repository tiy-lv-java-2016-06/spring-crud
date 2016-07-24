package com.theironyard;

import com.theironyard.services.BottleRepository;
import com.theironyard.services.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

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



}
