
package study.inno;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.HashSet;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

@Tag("task3")
public class SelenideTask3Test {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret123";

    // Список ID товаров, которые нужно удалить после теста
    private final List<Integer> createdProductIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        open(BASE_URL);
    }

    // Удаление тестовых товаров после каждого теста

    @AfterEach
    void tearDown() {

        try {
            for (Integer id : new HashSet<>(createdProductIds)) {

                var response = given()
                        .auth().preemptive().basic(USERNAME, PASSWORD)
                        .when()
                        .delete(BASE_URL + "/goods/" + id);

                System.out.println("Удаляем товар ID: " + id);
                System.out.println("DELETE status: " + response.statusCode());
                System.out.println("DELETE body: " + response.asString());

                response.then().statusCode(200);
            }

        } finally {
            closeWebDriver();
        }
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

    // Создание товара через API
    private int createProductViaApi(String name, double price) {

        // Создаём товар через API
        var response = given()
                .auth().preemptive().basic(USERNAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "name", name,
                        "price", price
                ))
                .when()
                .post(BASE_URL + "/goods/add");

        System.out.println("POST status: " + response.statusCode());
        System.out.println("POST body: " + response.asString());

        response.then().statusCode(200);

        // Получаем ID из ответа API
        int id = response.jsonPath().getInt("data.id");

        System.out.println("Создан товар с ID: " + id);

        // Проверяем, попал ли товар в первую страницу списка
        var listResponse = given()
                .when()
                .get(BASE_URL + "/goods/list?page=0&size=20");

        System.out.println("GET status: " + listResponse.statusCode());
        System.out.println("GET body: " + listResponse.asString());

        // Сохраняем ID для удаления после теста
        createdProductIds.add(id);

        return id;
    }


    // Добавление товара в корзину через интерфейс
    private void addProductToCart(int id, int quantity) {

        SelenideElement card = $("#card-" + id);

        card.shouldBe(visible);

        // Указываем необходимое количество
        $("#q-" + id).setValue(String.valueOf(quantity));

        // Добавляем товар в корзину
        card.$("button[data-action='add-to-cart']").click();
    }

    // Поиск товара в таблице админки
    private SelenideElement findAdminProductRow(String productName) {

        return $x(
                "//tbody[@id='tbody']//tr[.//input[@value='" +
                        productName + "']]"
        );
    }

    // --------------------------------------------------
    // ЗАДАЧА 3.1
    // Добавить три единицы товара и оформить заказ
    // --------------------------------------------------

    @Test
    void addThreeProductsAndMakeOrder() {

        String productName = "Test-Order-" +
                UUID.randomUUID().toString().substring(0, 8);

        // Создаём товар через API
        int productId = createProductViaApi(productName, 50);

        // Открываем витрину
        open(BASE_URL);

        // Добавляем 3 единицы товара
        addProductToCart(productId, 3);

        // Проверяем количество товаров в корзине
        $("#cart-count").shouldHave(text("3"));

        // Открываем корзину
        $("#open-cart-btn").click();

        // Проверяем название товара
        $("#cart-items").shouldHave(text(productName));

        // Проверяем общую стоимость: 50 * 3 = 150
        $("#total-price").shouldHave(text("150"));

        // Оформляем заказ
        $("#makeOrder").click();

        // Проверяем последнее уведомление после оформления заказа
        $$ ("#toast-container .toast")
                .last()
                .shouldBe(visible)
                .shouldHave(text("Заказ принят в обработку!"));
    }

    // --------------------------------------------------
    // ЗАДАЧА 3.2
    // Добавить разные товары и проверить общую стоимость
    // --------------------------------------------------

    @Test
    void checkTotalPriceOfDifferentProducts() {

        String firstName = "Test-First-" +
                UUID.randomUUID().toString().substring(0, 8);

        String secondName = "Test-Second-" +
                UUID.randomUUID().toString().substring(0, 8);

        // Создаём два разных товара через API
        int firstId = createProductViaApi(firstName, 40);
        int secondId = createProductViaApi(secondName, 75);

        // Открываем витрину
        open(BASE_URL);

        // Добавляем 2 единицы первого товара
        addProductToCart(firstId, 2);

        // Добавляем 1 единицу второго товара
        addProductToCart(secondId, 1);

        // Проверяем количество товаров
        $("#cart-count").shouldHave(text("3"));

        // Открываем корзину
        $("#open-cart-btn").click();

        // Проверяем наличие обоих товаров
        $("#cart-items").shouldHave(text(firstName));
        $("#cart-items").shouldHave(text(secondName));

        // Проверяем общую стоимость:
        // 40 * 2 + 75 * 1 = 155
        $("#total-price").shouldHave(text("155"));
    }

    // --------------------------------------------------
    // ЗАДАЧА 3.3
    // Добавить товар через админку и проверить уведомление
    // --------------------------------------------------

    @Test
    void addProductInAdminAndCheckNotification() {

        loginAsAdmin();

        String productName = "Test-Admin-" +
                UUID.randomUUID().toString().substring(0, 8);

        // Заполняем форму создания товара
        $("#n-name").setValue(productName);
        $("#n-price").setValue("70");

        // Нажимаем кнопку создания
        $("#add-btn").click();

        // Проверяем уведомление
        $("#toast-container .toast").shouldBe(visible);

        // Находим строку созданного товара в таблице админки
        SelenideElement row = findAdminProductRow(productName);

        row.shouldBe(visible);

// Получаем ID из первой ячейки строки
        int productId = Integer.parseInt(
                row.$("td").getText().trim()
        );

// Сохраняем ID для удаления в AfterEach
        createdProductIds.add(productId);

// Сохраняем ID для удаления в AfterEach
        createdProductIds.add(productId);
    }

    // --------------------------------------------------
    // ЗАДАЧА 3.4
    // Отредактировать товар через админку
    // и проверить изменения
    // --------------------------------------------------

    @Test
    void editProductInAdmin() {

        String oldName = "Test-Old-" +
                UUID.randomUUID().toString().substring(0, 8);

        String newName = "Test-New-" +
                UUID.randomUUID().toString().substring(0, 8);

        // Создаём исходный товар через API
        createProductViaApi(oldName, 50);

        // Переходим в админку
        loginAsAdmin();

        // Находим строку товара по старому названию
        SelenideElement row = findAdminProductRow(oldName);

        row.shouldBe(visible);

        // Меняем название
        row.$("input[type='text']").setValue(newName);

        // Меняем цену
        row.$("input[type='number']").setValue("99");

        // Нажимаем первую кнопку в строке — Сохранить
        row.$$("button").first().click();

        // Проверяем уведомление после сохранения
        $("#toast-container .toast").shouldBe(visible);

        // Возвращаемся на витрину
        open(BASE_URL);

        // Проверяем новое название и цену товара
        $(".product-card[data-name='" + newName + "']")
                .shouldBe(visible)
                .shouldHave(text("99"));
    }
}
