
package study.inno;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CartRemoveTest {

    // Открываем главную страницу перед тестом
    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
    }

    // Задача 1.2. Удалить добавленный товар из корзины
    // и проверить, что он больше не отображается
    @Test
    void removeProductFromCart() {

        // Находим первую карточку товара
        SelenideElement product =
                $$("#products-list .product-card").first();

        // Получаем название товара
        String productName = product.getAttribute("data-name");

        // Добавляем товар в корзину
        product.$("button[data-action='add-to-cart']").click();

        // Проверяем, что счётчик корзины показывает 1
        $("#cart-count").shouldHave(text("1"));

        // Открываем корзину
        $("#open-cart-btn").click();

        // Проверяем, что товар появился в корзине
        $("#cart-items").shouldHave(text(productName));

        // Удаляем товар из корзины
        $("#cart-items [data-action='remove']").click();

        // Проверяем, что товар больше не существует в DOM
        $("#cart-items .cart-item").shouldNot(exist);

        // Проверяем, что отображается сообщение о пустой корзине
        $("#empty-cart").shouldBe(visible)
                .shouldHave(text("Пусто"));

        // Проверяем, что счётчик корзины равен 0
        $("#cart-count").shouldHave(text("0"));
    }
}
