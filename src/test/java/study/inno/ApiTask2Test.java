package study.inno;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTask2Test {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "secret123";

    // Создаём уникальное название товара
    private String uniqueName(String baseName) {
        return baseName + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    // Формируем данные товара
    private Map<String, Object> createProductBody(String name) {
        Map<String, Object> product = new HashMap<>();
        product.put("name", name);
        product.put("price",
                ThreadLocalRandom.current().nextInt(100, 1000));
        return product;
    }

    // Создаём товар и получаем его ID из списка
    private long createProductAndGetId(String name) {
        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(createProductBody(name))
                .log().all()
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .queryParam("page", 0)
                        .queryParam("size", 100)
                        .when()
                        .get("/goods/list")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        List<Map<String, Object>> goods =
                response.jsonPath().getList("goods");

        assertNotNull(goods, "Список товаров не должен быть null");

        for (Map<String, Object> good : goods) {
            if (name.equals(good.get("name"))) {
                return ((Number) good.get("id")).longValue();
            }
        }

        throw new AssertionError(
                "Созданный товар не найден в списке: " + name);
    }

    // Задача 2.1 — POST /goods/add
    @Test
    @Tag("api")
    @Tag("task2")
    void addGoods() {
        String name = uniqueName("Апельсин");

        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(createProductBody(name))
                .log().all()
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        Response response =
                given()
                        .baseUri(BASE_URL)
                        .queryParam("page", 0)
                        .queryParam("size", 100)
                        .when()
                        .get("/goods/list")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        List<Map<String, Object>> goods =
                response.jsonPath().getList("goods");

        assertTrue(
                goods.stream().anyMatch(
                        good -> name.equals(good.get("name"))),
                "Созданный товар должен присутствовать в списке");
    }

    // Задача 2.2 — GET /goods/{id}
    @Test
    @Tag("api")
    @Tag("task2")
    void getGoodsById() {
        String name = uniqueName("Банан");
        long id = createProductAndGetId(name);

        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .log().all()
                .when()
                .get("/goods/{id}", id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(name));
    }

    // Задача 2.3 — DELETE /goods/{id}
    @Test
    @Tag("api")
    @Tag("task2")
    void deleteGoodsById() {
        String name = uniqueName("Яблоко");
        long id = createProductAndGetId(name);

        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .log().all()
                .when()
                .delete("/goods/{id}", id)
                .then()
                .log().all()
                .statusCode(200);

        // Проверяем, что удалённый товар больше не находится по ID
        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .when()
                .get("/goods/{id}", id)
                .then()
                .statusCode(404);
    }

    // Задача 2.4 — PATCH /goods/{id}
    @Test
    @Tag("api")
    @Tag("task2")
    void updateGoodsById() {
        String oldName = uniqueName("Груша");
        long id = createProductAndGetId(oldName);

        String newName = uniqueName("Манго");

        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .contentType(ContentType.JSON)
                .body(createProductBody(newName))
                .log().all()
                .when()
                .patch("/goods/{id}", id)
                .then()
                .log().all()
                .statusCode(200);

        // Проверяем новое название через GET /goods/{id}
        given()
                .baseUri(BASE_URL)
                .auth().basic(USERNAME, PASSWORD)
                .log().all()
                .when()
                .get("/goods/{id}", id)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo(newName));
    }

    // Задача 2.5 — GET /goods/list
    @Test
    @Tag("api")
    @Tag("task2")
    void getGoodsList() {
        given()
                .baseUri(BASE_URL)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .log().all()
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods", org.hamcrest.Matchers.notNullValue());
    }
}
