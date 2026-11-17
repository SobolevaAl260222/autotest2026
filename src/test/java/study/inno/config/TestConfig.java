
package study.inno.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {

    private static final Properties properties = new Properties();
    private static final JsonNode startProduct;

    static {
        // Читаем config.properties
        try (InputStream input =
                     TestConfig.class.getResourceAsStream("/config.properties")) {

            if (input == null) {
                throw new RuntimeException("Файл config.properties не найден");
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения config.properties", e);
        }

        // Читаем start-product.json
        try (InputStream input =
                     TestConfig.class.getResourceAsStream("/start-product.json")) {

            if (input == null) {
                throw new RuntimeException("Файл start-product.json не найден");
            }

            ObjectMapper mapper = new ObjectMapper();
            startProduct = mapper.readTree(input);

        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения start-product.json", e);
        }
    }

    // URL сайта
    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    // URL API
    public static String getApiUrl() {
        return properties.getProperty("api.url");
    }

    // Тайм-аут
    public static long getTimeout() {
        return Long.parseLong(
                properties.getProperty("timeout.global")
        );
    }

    // Режим логирования
    public static boolean isLoggingEnabled() {
        return Boolean.parseBoolean(
                properties.getProperty("logging.enabled")
        );
    }

    // Логин администратора
    public static String getAdminUsername() {
        return properties.getProperty("admin.username");
    }

    // Пароль администратора
    public static String getAdminPassword() {
        return properties.getProperty("admin.password");
    }

    // Название стартового товара из JSON
    public static String getProductName() {
        return startProduct.get("name").asText();
    }

    // Цена стартового товара из JSON
    public static double getProductPrice() {
        return startProduct.get("price").asDouble();
    }

    public static void printConfig() {
        System.out.println("URL стенда: " + getBaseUrl());
        System.out.println("URL API: " + getApiUrl());
        System.out.println("Тайм-аут: " + getTimeout());
        System.out.println("Логирование включено: " + isLoggingEnabled());
        System.out.println("Стартовый товар: " + getProductName());
        System.out.println("Цена стартового товара: " + getProductPrice());
    }
}
