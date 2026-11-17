
package study.inno;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;

import study.inno.config.TestConfig;

public class BaseTest {

    private static final Logger log =
            LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    static void printConfig() {
        TestConfig.printConfig();
    }

    @BeforeEach
    void setUp() {

        // Настраиваем тайм-аут Selenide
        Configuration.timeout = TestConfig.getTimeout();

        // Открываем сайт из конфигурации
        open(TestConfig.getBaseUrl());

        log.info("Открыта главная страница");
    }
}
