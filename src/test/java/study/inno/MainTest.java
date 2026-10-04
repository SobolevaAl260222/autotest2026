package study.inno;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {
    private final Random random = new Random();

    // ============================================================
    // ЗАДАЧА 1
    // 4 автотеста с информативными AssertJ-ассертами
    // ============================================================

    @Tag("task1")
    @Test
    void testIsEven() {

        int number = 4;

        boolean expected = false; // специально неверное ожидаемое значение
        boolean actual = Main.isEven(number);

        assertThat(actual)
                .as("Проверка isEven. Число: %d. Ожидалось: %s, получено: %s",
                        number, expected, actual)
                .isEqualTo(expected);
    }


    @Tag("task1")
    @Test
    void testCheckAccess() {

        int age = random.nextInt(100);

        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = Main.checkAccess(age);

        assertThat(actual)
                .as("Проверка checkAccess. Возраст: %d. Ожидалось: %s, получено: %s",
                        age, expected, actual)
                .isEqualTo(expected);
    }


    @Tag("task1")
    @Test
    void testGetEvenInRange() {

        List<Integer> actual = Main.getEvenInRange(1, 10);

        List<Integer> expected = Arrays.asList(2, 4, 6, 8, 10);

        assertThat(actual)
                .as("Проверка getEvenInRange. Ожидалось: %s, получено: %s",
                        expected, actual)
                .containsExactlyElementsOf(expected);
    }


    @Tag("task1")
    @Test
    void testReverse() {

        String[] input = {"A", "B", "C"};

        String[] actual = Main.reverse(input);

        String[] expected = {"C", "B", "A"};

        assertThat(actual)
                .as("Проверка reverse. Ожидалось: %s, получено: %s",
                        Arrays.toString(expected),
                        Arrays.toString(actual))
                .containsExactly(expected);
    }

    // ============================================================
    // ЗАДАЧА 2
    // Каждый тест запускается 10 раз
    // Все тесты имеют @Tag("task2")
    // ============================================================

    @Tag("task2")
    @RepeatedTest(10)
    void testIsEvenTask2() {
        int number = random.nextInt(100);
        boolean expected = number % 2 == 0;
        boolean actual = Main.isEven(number);
        assertThat(actual)
                .as("isEven(%d): ожидалось %s, получено %s",
                        number, expected, actual)
                .isEqualTo(expected);
    }


    // 2. checkAccess
    @Tag("task2")
    @RepeatedTest(10)
    void testCheckAccessTask2() {

        int age = random.nextInt(100);

        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = Main.checkAccess(age);

        assertThat(actual)
                .as("checkAccess(%d): ожидалось %s, получено %s",
                        age, expected, actual)
                .isEqualTo(expected);
    }


    // 3. isPositive
    @Tag("task2")
    @RepeatedTest(10)
    void testIsPositiveTask2() {

        int number = random.nextInt(201) - 100;

        boolean expected = number > 0;
        boolean actual = Main.isPositive(number);

        assertThat(actual)
                .as("isPositive(%d): ожидалось %s, получено %s",
                        number, expected, actual)
                .isEqualTo(expected);
    }


    // 4. getGrade
    @Tag("task2")
    @RepeatedTest(10)
    void testGetGradeTask2() {

        int score = random.nextInt(102);

        String expected;

        if (score < 0 || score > 100) {
            expected = "Error";
        } else if (score <= 20) {
            expected = "E";
        } else if (score <= 40) {
            expected = "D";
        } else if (score <= 60) {
            expected = "C";
        } else if (score <= 80) {
            expected = "B";
        } else {
            expected = "A";
        }

        String actual = Main.getGrade(score);

        assertThat(actual)
                .as("getGrade(%d): ожидалось %s, получено %s",
                        score, expected, actual)
                .isEqualTo(expected);
    }



    // 5. blastOff
    @Tag("task2")
    @RepeatedTest(10)
    void testBlastOffTask2() {

        int start = random.nextInt(10) + 1;

        StringBuilder expectedBuilder = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            if (expectedBuilder.length() > 0) {
                expectedBuilder.append(" ");
            }
            expectedBuilder.append(i);
        }

        String expected = expectedBuilder.toString();
        String actual = Main.blastOff(start);

        assertThat(actual)
                .as("blastOff(%d): ожидалось %s, получено %s",
                        start, expected, actual)
                .isEqualTo(expected);
    }


    // 6. sumToN
    @Tag("task2")
    @RepeatedTest(10)
    void testSumToNTask2() {

        int n = random.nextInt(20) + 1;

        int expected = n * (n + 1) / 2;
        int actual = Main.sumToN(n);

        assertThat(actual)
                .as("sumToN(%d): ожидалось %d, получено %d",
                        n, expected, actual)
                .isEqualTo(expected);
    }


    // 7. hasBug
    @Tag("task2")
    @RepeatedTest(10)
    void testHasBugTask2() {

        String[] messages = {
                "Hello",
                "Bug found",
                "Test message",
                "Java"
        };

        boolean actual = Main.hasBug(messages[1]);

        assertThat(actual)
                .as("hasBug: строка '%s' должна содержать Bug",
                        messages[1])
                .isTrue();
    }



    // 8. getEvenInRange
    @Tag("task2")
    @RepeatedTest(10)
    void testGetEvenInRangeTask2() {

        int start = 1;
        int end = 10;

        List<Integer> expected = Arrays.asList(2, 4, 6, 8, 10);
        List<Integer> actual = Main.getEvenInRange(start, end);

        assertThat(actual)
                .as("getEvenInRange(%d, %d): ожидалось %s, получено %s",
                        start, end, expected, actual)
                .containsExactlyElementsOf(expected);
    }


    // 9. findMax
    @Tag("task2")
    @RepeatedTest(10)
    void testFindMaxTask2() {

        int[] numbers = {3, 7, 2, 10, 5};

        int expected = 10;
        int actual = Main.findMax(numbers);

        assertThat(actual)
                .as("findMax: массив %s. Ожидалось %d, получено %d",
                        Arrays.toString(numbers), expected, actual)
                .isEqualTo(expected);
    }


    // 10. reverse
    @Tag("task2")
    @RepeatedTest(10)
    void testReverseTask2() {

        String[] input = {"A", "B", "C"};

        String[] expected = {"C", "B", "A"};
        String[] actual = Main.reverse(input);

        assertThat(actual)
                .as("reverse: ожидалось %s, получено %s",
                        Arrays.toString(expected),
                        Arrays.toString(actual))
                .containsExactly(expected);
    }


    // 11. calcAverage
    @Tag("task2")
    @RepeatedTest(10)
    void testCalcAverageTask2() {

        List<Integer> numbers = Arrays.asList(10, 20, 30);

        double expected = 20.0;
        double actual = Main.calcAverage(numbers);

        assertThat(actual)
                .as("calcAverage: список %s. Ожидалось %.2f, получено %.2f",
                        numbers, expected, actual)
                .isEqualTo(expected);
    }


    // 12. removeSpecificName
    @Tag("task2")
    @RepeatedTest(10)
    void testRemoveSpecificNameTask2() {

        List<String> names = Arrays.asList(
                "Anna",
                "Ivan",
                "Maria",
                "Ivan"
        );

        List<String> expected = Arrays.asList(
                "Anna",
                "Maria"
        );

        List<String> actual = Main.removeSpecificName(
                names,
                "Ivan"
        );

        assertThat(actual)
                .as("removeSpecificName: ожидалось %s, получено %s",
                        expected, actual)
                .containsExactlyElementsOf(expected);
    }
}


