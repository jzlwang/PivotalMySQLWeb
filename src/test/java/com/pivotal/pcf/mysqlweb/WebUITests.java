package com.pivotal.pcf.mysqlweb;

import com.pivotal.pcf.mysqlweb.controller.LoginController;
import com.pivotal.pcf.mysqlweb.controller.TableController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebAppConfiguration
public class WebUITests extends PivotalMySqlWebApplicationTests
{
    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void contextLoads() {
    }

    @Test public void testLoginPage() throws Exception {
        mockMvc.perform(get("/").accept(MediaType.TEXT_HTML))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test public void testLoginMySQL() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "pas")
                .param("password", "pas")
                .param("url", "jdbc:mysql://localhost:3306/employees")
                .accept(MediaType.TEXT_HTML))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("main"));
    }

    @Configuration
    public static class TestConfiguration
    {
        @Bean
        public LoginController loginController()
        {
            return new LoginController();
        }

        @Bean
        public TableController tableController()
        {
            return new TableController();
        }
    }
}
