package com.conpany.project;

import com.company.project.web.async.TestApi;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.Future;

@EnableAsync
public class AsyncTester extends Tester {

    //模拟mvc环境
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestApi testApi;
    @Before
    public void init(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void test() throws Exception {
        Future<String> f1 =  testApi.doTaskOne();
        Future<String> f2 = testApi.doTaskTwo();
        Future<String> f3 =  testApi.doTaskThree();
        while(true){
            if(f1.isDone()&&f2.isDone()&&f3.isDone()){
                break;
            }
        }

        System.out.println(f1.get()+" "+f2.get()+" "+f3.get());
    }
}
