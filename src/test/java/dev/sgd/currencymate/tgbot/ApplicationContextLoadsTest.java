package dev.sgd.currencymate.tgbot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ApplicationContextLoadsTest {

    @MockitoBean
    private CommandLineRunner commandLineRunner;

    @Test
    void contextLoads() {
    }

}