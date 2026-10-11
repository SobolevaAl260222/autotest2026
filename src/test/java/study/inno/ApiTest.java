package study.inno;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import io.restassured.response.Response;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.Matchers.hasItem;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.empty;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiTest {

    private static final String BASE_URL = "http://localhost:8080";

    // Задача 1.1
    // Проверка GET /goods/list через given(), when(), then()
    @Test
    @Tag("api")
    @Tag("task1")
    void getGoodsListWithGivenWhenThen() {

        given()
                .baseUri(BASE_URL)
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }


    // Задача 1.2
    @Test
    @Tag("api")
    @Tag("task1")
    void getGoodsListWithRequestSpecification() {
        RequestSpecification request = given()
                .baseUri(BASE_URL)
                .queryParam("page", 0)
                .queryParam("size", 10);

        request.when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", empty());
    }


    // Задача 1.3
    @Test
    @Tag("api")
    @Tag("task1")
    void createGoodsAndVerifyWithRestAssured() {
        String productName = "Апельсин";

        Map<String, Object> product = Map.of(
                "name", productName,
                "price", ThreadLocalRandom.current().nextInt(100, 1000)
        );

        // Создаём товар
        given()
                .auth().basic("admin", "secret123")
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        // Получаем список и проверяем наличие товара
        given()
                .baseUri(BASE_URL)
                .queryParam("page", 0)
                .queryParam("size", 100)
                .log().all()
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem(productName));
    }

    // Задача 1.4
    @Test
    @Tag("api")
    @Tag("task1")
    void createGoodsAndVerifyWithAssertJ() {
        String productName = "Банан";

        Map<String, Object> product = Map.of(
                "name", productName,
                "price", ThreadLocalRandom.current().nextInt(100, 1000)
        );

        // Создаём товар
        given()
                .auth().basic("admin", "secret123")
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(product)
                .log().all()
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .statusCode(200);

        // Получаем список товаров
        Response response = given()
                .baseUri(BASE_URL)
                .queryParam("page", 0)
                .queryParam("size", 100)
                .log().all()
                .when()
                .get("/goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();

        List<Map<String, Object>> goods =
                response.jsonPath().getList("goods");

        // Проверяем наличие товара через AssertJ
        assertThat(goods)
                .as("Созданный товар должен присутствовать в списке")
                .anySatisfy(item ->
                        assertThat(item.get("name"))
                                .isEqualTo(productName)
                );
    }
}