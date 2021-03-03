package com.ehigon.tasks.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RepeatOptionsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    private MockMvc mockMvc;

    @Test
    public void given_service_when_getRepeatOptions_then_repeatOptionsAreReturned() throws Exception {
        mockMvc.perform(get("/repeat_options"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void setUp() {
        mockMvc();
    }

    private void mockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
