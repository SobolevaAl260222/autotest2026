
package study.inno;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret123";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Авторизация администратора
    private void loginAsAdmin() {
        driver.get(BASE_URL + "/login");

        driver.findElement(By.id("username")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);

        driver.findElement(By.cssSelector("button.primary")).click();

        // Ждём завершения авторизации и перехода в админку
        wait.until(ExpectedConditions.urlContains("/admin"));

        // Ждём появления поля создания товара
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("n-name")
        ));
    }

    // Задача 1.1 — добавить товар через админку
    // и проверить его отображение на витрине
    @Test
    void addProductThroughAdmin() {

        loginAsAdmin();

        String productName = "Test-" +
                UUID.randomUUID().toString().substring(0, 8);

        driver.findElement(By.id("n-name"))
                .sendKeys(productName);

        driver.findElement(By.id("n-price"))
                .sendKeys("25");

        driver.findElement(By.id("add-btn")).click();

        driver.get(BASE_URL);

        WebElement product = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(
                                ".product-card[data-name='" + productName + "']"
                        )
                )
        );

        assertTrue(
                product.isDisplayed(),
                "Добавленный товар должен отображаться на витрине"
        );
    }

    // Задача 1.2 — добавить товар в корзину
    @Test
    void addProductToCart() {

        WebElement product = driver.findElement(
                By.cssSelector(".product-card[data-name='Банан']")
        );

        product.findElement(
                By.cssSelector("[data-action='add-to-cart']")
        ).click();

        driver.findElement(By.id("open-cart-btn")).click();

        WebElement cart = driver.findElement(By.id("cart-items"));

        assertTrue(
                cart.getText().contains("Банан"),
                "Товар должен отображаться в корзине"
        );
    }

    // Задача 1.3 — попытка входа с неправильным логином и паролем
    @Test
    void loginWithIncorrectCredentials() {

        driver.get(BASE_URL + "/login");

        driver.findElement(By.id("username"))
                .sendKeys("wrongUser");

        driver.findElement(By.id("password"))
                .sendKeys("wrongPassword");

        driver.findElement(By.cssSelector("button.primary"))
                .click();

        // Проверяем, что после неверных данных
        // пользователь остался на странице авторизации
        wait.until(ExpectedConditions.urlContains("/login"));

        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "При неверном логине и пароле вход не должен выполняться"
        );

        // Проверяем, что форма авторизации доступна
        wait.until(driver ->
                !driver.findElements(By.id("username")).isEmpty()
        );
    }


// Задача 1.4 — проверка сохранения корзины после обновления
@Test
void cartIsSavedAfterRefresh() {

    // Открываем главную страницу
    driver.get(BASE_URL);

    // Находим карточку товара «Банан»
    WebElement product = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//div[contains(@class,'product-card')]" +
                                    "[.//*[normalize-space()='Банан']]"
                    )
            )
    );

    // Добавляем товар в корзину
    product.findElement(
            By.xpath(".//button[contains(normalize-space(.),'В корзину')]")
    ).click();

    // Проверяем количество товаров до обновления
    wait.until(
            ExpectedConditions.textToBePresentInElementLocated(
                    By.id("cart-count"), "1"
            )
    );

    System.out.println("До обновления: " +
            driver.findElement(By.id("cart-count")).getText());

    // Обновляем страницу
    driver.navigate().refresh();

    // Проверяем значение счётчика после обновления
    wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart-count")
            )
    );

    String countAfterRefresh =
            driver.findElement(By.id("cart-count")).getText();

    System.out.println("После обновления: " + countAfterRefresh);

    // Открываем корзину
    driver.findElement(By.id("open-cart-btn")).click();

    // Проверяем содержимое корзины
    WebElement cartItems = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.id("cart-items")
            )
    );

    assertTrue(
            cartItems.getText().contains("Банан"),
            "Корзина должна сохранять товар после обновления страницы"
    );
}
}

