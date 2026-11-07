
package study.inno;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret123";

    @BeforeEach
    void setUp() {
        open(BASE_URL);
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    // Авторизация администратора
    private void loginAsAdmin() {

        open(BASE_URL + "/login");

        $("#username").setValue(USERNAME);
        $("#password").setValue(PASSWORD);

        $("button.primary").click();

        open(BASE_URL + "/admin");

        $("#n-name").shouldBe(visible);
    }

    // Задача 2.1 — добавить товар через админку
    // и проверить его отображение на витрине
    @Test
    void addProductThroughAdmin() {

        loginAsAdmin();

        String productName = "Test-" +
                UUID.randomUUID().toString().substring(0, 8);

        $("#n-name").setValue(productName);
        $("#n-price").setValue("25");

        $("#add-btn").click();

        open(BASE_URL);

        $(".product-card[data-name='" + productName + "']")
                .shouldBe(visible);
    }

    // Задача 2.2 — добавить товар в корзину
    @Test
    void addProductToCart() {

        $(".product-card[data-name='Банан']")
                .$("[data-action='add-to-cart']")
                .click();

        $("#open-cart-btn").click();

        $("#cart-items").shouldHave(text("Банан"));
    }

    // Задача 2.3 — попытка входа с неправильными данными
    @Test
    void loginWithIncorrectCredentials() {

        open(BASE_URL + "/login");

        $("#username").setValue("wrongUser");
        $("#password").setValue("wrongPassword");

        $("button.primary").click();

        $("#username").shouldBe(visible);
    }


// Задача 2.4 — проверка сохранения корзины после обновления
@Test
void cartIsSavedAfterRefresh() {
    open(BASE_URL);

    $(".product-card[data-name='Банан']")
            .shouldBe(visible)
            .$("button[data-action='add-to-cart']")
            .click();

    // Проверяем добавление до обновления
    $("#cart-count").shouldHave(text("1"));

    // Обновляем страницу
    refresh();

    // Проверяем сохранение товара
    $("#cart-count").shouldHave(text("1"));
}

// Задача 2.5 — заказ на сумму более 300 рублей
@Test
void makeOrderOver300Rubles() {

    // Добавляем банан стоимостью 575 рублей
    $(".product-card[data-name='Банан']")
            .$("[data-action='add-to-cart']")
            .click();

    $("#open-cart-btn").click();

    $("#cart-items").shouldHave(text("Банан"));

    // Оформляем заказ
    $("#makeOrder").click();

    // Проверяем появление JavaScript Alert
    String alertText = switchTo().alert().getText();

    assert alertText != null && !alertText.isBlank();

    switchTo().alert().accept();
}
}
