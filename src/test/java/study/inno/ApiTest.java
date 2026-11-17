
package study.inno;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import study.inno.config.TestConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.not;

import study.inno.config.TestConfig;

public class ApiTest {
    @BeforeAll
    static void printConfig() {
        TestConfig.printConfig();
    }

    // Задача 1.1
    // Проверка GET /goods/list через given(), when(), then()
    @Test
    @Tag("api")
    @Tag("task1")
    void getGoodsListWithGivenWhenThen() {

        given()
                .baseUri(TestConfig.getApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", not(empty()));
    }

    // Задача 1.2
    @Test
    @Tag("api")
    @Tag("task1")
    void getGoodsListWithRequestSpecification() {

        RequestSpecification request = given()
                .baseUri(TestConfig.getApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 10);

        request.when()
                .get("/goods/list")
                .then()
                .statusCode(200)
                .body("goods", not(empty()));
    }

    // Задача 1.3
    // Создание товара и проверка через RestAssured
    @Test
    @Tag("api")
    @Tag("task1")
    void createGoodsAndVerifyWithRestAssured() {

        String productName = TestConfig.getProductName()
                + "-" + UUID.randomUUID().toString().substring(0, 8);

        Map<String, Object> product = Map.of(
                "name", productName,
                "price", TestConfig.getProductPrice()
        );

        // Создаём товар
        given()
                .auth().basic(
                        TestConfig.getAdminUsername(),
                        TestConfig.getAdminPassword()
                )
                .baseUri(TestConfig.getApiUrl())
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
                .baseUri(TestConfig.getApiUrl())
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
    // Создание товара и проверка через AssertJ
    @Test
    @Tag("api")
    @Tag("task1")
    void createGoodsAndVerifyWithAssertJ() {

        String productName = TestConfig.getProductName()
                + "-" + UUID.randomUUID().toString().substring(0, 8);

        Map<String, Object> product = Map.of(
                "name", productName,
                "price", TestConfig.getProductPrice()
        );

        // Создаём товар
        given()
                .auth().basic(
                        TestConfig.getAdminUsername(),
                        TestConfig.getAdminPassword()
                )
                .baseUri(TestConfig.getApiUrl())
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
                .baseUri(TestConfig.getApiUrl())
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
