package study.inno;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.interactions.Actions;
import java.math.BigDecimal;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

import study.inno.config.TestConfig;

public class DragAndDropTest extends BaseTest {

    // Задача 1.1. Перетащить товар в корзину с помощью Drag-and-Drop
    @Test
    void dragProductToCart() {

        // Получаем название и цену товара из JSON
        String productName = TestConfig.getProductName();
        double productPrice = TestConfig.getProductPrice();

        // Находим карточку товара по названию из конфигурации
        SelenideElement product = $$("#products-list .product-card")
                .findBy(attribute("data-name", productName))
                .shouldHave(attribute("data-price",
                        BigDecimal.valueOf(productPrice)
                                .stripTrailingZeros()
                                .toPlainString()));

        // Находим кнопку корзины
        SelenideElement cartButton = $("#open-cart-btn");

        // Перетаскиваем товар в корзину
        new Actions(WebDriverRunner.getWebDriver())
                .clickAndHold(product)
                .moveToElement(cartButton)
                .release()
                .perform();

        // Проверяем, что счётчик корзины показывает 1
        $("#cart-count").shouldHave(text("1"));

        // Открываем корзину
        cartButton.click();

        // Проверяем, что товар отображается в корзине
        $("#cart-items").shouldHave(text(productName));
    }
}