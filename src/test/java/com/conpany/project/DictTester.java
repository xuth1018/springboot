package com.conpany.project;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class DictTester extends Tester {
    //模拟mvc环境
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getDic() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("").param("",""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
