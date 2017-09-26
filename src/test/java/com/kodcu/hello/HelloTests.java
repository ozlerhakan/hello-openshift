package com.kodcu.hello;

import com.kodcu.controller.ServiceController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static junit.framework.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hakan on 26/09/2017
 */

@RunWith(SpringRunner.class)
@WebMvcTest(ServiceController.class)
public class HelloTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldPrintHello() throws UnknownHostException {

        try {

            String hello = "hello";
            String containerId = InetAddress.getLocalHost().getHostName();

            String expected = String.join(" ", hello, containerId);

            this.mockMvc.perform(get("/welcome/hello"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(expected));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
