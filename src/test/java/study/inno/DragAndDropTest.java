
package study.inno;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DragAndDropTest {

    // Открываем главную страницу перед тестом
    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
    }

    // Задача 1.1. Перетащить товар в корзину с помощью Drag-and-Drop
    @Test
    void dragProductToCart() {

        // Находим первую карточку товара
        SelenideElement product =
                $$("#products-list .product-card").first();

        // Получаем название товара
        String productName = product.getAttribute("data-name");

        // Находим кнопку корзины
        SelenideElement cartButton = $("#open-cart-btn");

        // Выполняем Drag-and-Drop с помощью Selenium Actions
        new Actions(WebDriverRunner.getWebDriver())
                .clickAndHold(product)
                .moveToElement(cartButton)
                .release()
                .perform();

        // Проверяем, что товар добавился в корзину
        $("#cart-count").shouldHave(text("1"));

        // Открываем корзину
        cartButton.click();

        // Проверяем наличие товара в корзине
        $("#cart-items").shouldHave(text(productName));
    }
}
