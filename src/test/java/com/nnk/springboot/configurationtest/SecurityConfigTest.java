package com.nnk.springboot.configurationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testUserHasAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "ANOTHER_ROLE")
    public void testUserWithoutAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/someEndpoint"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

}
