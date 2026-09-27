package study.inno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    // ============================================================
    // ЗАДАЧА 1
    // ============================================================

    // 1. isEven
    // Запускаем метод один раз со случайным числом от 1 до 100
    @Test
    void testIsEven() {

        System.out.println("====================Test method start");

        int number = (int) (Math.random() * 100) + 1;

        boolean expected = number % 2 == 0;
        boolean actual = Main.isEven(number);

        assertEquals(expected, actual);

        System.out.println("Number: " + number);
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);

        System.out.println("Test method end");
        System.out.println("==========================");
    }


    // 2. checkAccess
    // Запускаем метод 20 раз со случайными числами от 0 до 99
    @RepeatedTest(20)
    void testCheckAccess() {

        System.out.println("====================Test method start");

        int age = (int) (Math.random() * 100);

        String expected;

        if (age > 18) {
            expected = "Allowed";
        } else {
            expected = "Denied";
        }

        String actual = Main.checkAccess(age);

        assertEquals(expected, actual);

        System.out.println("Age: " + age);
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);

        System.out.println("Test method end");
        System.out.println("==========================");
    }


    // 3. getGrade
    // Данные берутся из CSV
    @ParameterizedTest
    @CsvFileSource(
            resources = "/data.csv",
            numLinesToSkip = 1
    )
    void testGetGrade(int score, String expected) {

        System.out.println("====================Test method start");

        String actual = Main.getGrade(score);

        assertEquals(expected, actual);

        System.out.println("Score: " + score);
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);

        System.out.println("Test method end");
        System.out.println("==========================");
    }


    // ============================================================
    // ЗАДАЧА 2
    // Тестируем все 12 методов
    // ============================================================


    // 1. isEven
    @Test
    void testIsEvenTask2() {

        boolean actual = Main.isEven(10);
        boolean expected = true;

        assertEquals(expected, actual);

        printResult("isEven", expected, actual);
    }


    // 2. checkAccess
    @Test
    void testCheckAccessTask2() {

        int age = 20;

        String actual = Main.checkAccess(age);

        String expected = "Allowed";

        assertEquals(expected, actual);

        printResult("checkAccess", expected, actual);
    }


    // 3. isPositive
    @Test
    void testIsPositive() {

        boolean actual = Main.isPositive(10);
        boolean expected = true;

        assertEquals(expected, actual);

        printResult("isPositive", expected, actual);
    }


    // 4. getGrade
    @ParameterizedTest
    @ValueSource(ints = {
            0,
            20,
            21,
            40,
            41,
            60,
            61,
            80,
            81,
            100,
            101
    })
    void testGetGradeTask2(int score) {

        String expected;

        if (score >= 0 && score <= 20) {
            expected = "E";
        } else if (score >= 21 && score <= 40) {
            expected = "D";
        } else if (score >= 41 && score <= 60) {
            expected = "C";
        } else if (score >= 61 && score <= 80) {
            expected = "B";
        } else if (score >= 81 && score <= 100) {
            expected = "A";
        } else {
            expected = "Error";
        }

        String actual = Main.getGrade(score);

        assertEquals(expected, actual);

        printResult("getGrade(" + score + ")", expected, actual);
    }


    // 5. blastOff
    @Test
    void testBlastOff() {

        String actual = Main.blastOff(5);

        String expected = "5 4 3 2 1 ";

        assertEquals(expected, actual);

        printResult("blastOff", expected, actual);
    }


    // 6. sumToN
    @Test
    void testSumToN() {

        int actual = Main.sumToN(5);

        int expected = 15;

        assertEquals(expected, actual);

        printResult("sumToN", expected, actual);
    }


    // 7. hasBug
    @ParameterizedTest
    @ValueSource(strings = {
            "Bug",
            "bug",
            "BUG",
            "Something Bug happened"
    })
    void testHasBug(String message) {

        boolean actual = Main.hasBug(message);

        boolean expected = true;

        assertEquals(expected, actual);

        printResult("hasBug", expected, actual);
    }


    // 8. getEvenInRange
    @Test
    void testGetEvenInRange() {

        List<Integer> actual = Main.getEvenInRange(1, 10);

        List<Integer> expected = Arrays.asList(2, 4, 6, 8, 10);

        assertEquals(expected, actual);

        printResult("getEvenInRange", expected, actual);
    }


    // 9. findMax
    @Test
    void testFindMax() {

        int[] numbers = {3, 7, 2, 10, 5};

        int actual = Main.findMax(numbers);

        int expected = 10;

        assertEquals(expected, actual);

        printResult("findMax", expected, actual);
    }


    // 10. reverse
    @Test
    void testReverse() {

        String[] input = {
                "A",
                "B",
                "C"
        };

        String[] actual = Main.reverse(input);

        String[] expected = {
                "C",
                "B",
                "A"
        };

        assertArrayEquals(expected, actual);

        printResult(
                "reverse",
                Arrays.toString(expected),
                Arrays.toString(actual)
        );
    }


    // 11. calcAverage
    @Test
    void testCalcAverage() {

        List<Integer> numbers = Arrays.asList(
                10,
                20,
                30
        );

        double actual = Main.calcAverage(numbers);

        double expected = 20.0;

        assertEquals(expected, actual);

        printResult("calcAverage", expected, actual);
    }


    // 12. removeSpecificName
    @Test
    void testRemoveSpecificName() {

        List<String> names = Arrays.asList(
                "Anna",
                "Ivan",
                "Maria",
                "Ivan"
        );

        List<String> actual = Main.removeSpecificName(
                names,
                "Ivan"
        );

        List<String> expected = Arrays.asList(
                "Anna",
                "Maria"
        );

        assertEquals(expected, actual);

        printResult("removeSpecificName", expected, actual);
    }


    // ============================================================
    // ВСПОМОГАТЕЛЬНЫЙ МЕТОД
    // ============================================================

    private void printResult(
            String methodName,
            Object expected,
            Object actual
    ) {

        System.out.println("==============================");
        System.out.println("Method: " + methodName);
        System.out.println("Expected: " + expected);
        System.out.println("Actual: " + actual);

        if (expected.equals(actual)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        System.out.println("==============================");
    }
}