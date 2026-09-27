package study.inno;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // 1. isEven
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    // 2. checkAccess
    public static boolean checkAccess(int age) {
        return age >= 18;
    }

    // 3. isPositive
    public static boolean isPositive(int number) {
        return number > 0;
    }

    // 4. getGrade
    public static String getGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // 5. blastOff
    public static String blastOff(int number) {
        StringBuilder result = new StringBuilder();

        for (int i = number; i >= 1; i--) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(i);
        }

        return result.toString();
    }

    // 6. sumToN
    public static int sumToN(int n) {
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        return sum;
    }

    // 7. hasBug
    public static boolean hasBug(String text) {
        if (text == null) {
            return false;
        }

        return text.toLowerCase().contains("bug");
    }

    // 8. getEvenInRange
    public static List<Integer> getEvenInRange(int start, int end) {
        List<Integer> result = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                result.add(i);
            }
        }

        return result;
    }

    // 9. findMax
    public static int findMax(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }

        int max = numbers[0];

        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }

        return max;
    }

    // 10. reverse
    public static String[] reverse(String[] input) {
        String[] result = new String[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = input[input.length - 1 - i];
        }

        return result;
    }

    // 11. calcAverage
    public static double calcAverage(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }

        int sum = 0;

        for (int number : numbers) {
            sum += number;
        }

        return (double) sum / numbers.size();
    }

    // 12. removeSpecificName
    public static List<String> removeSpecificName(
            List<String> names,
            String nameToRemove
    ) {
        List<String> result = new ArrayList<>();

        for (String name : names) {
            if (!name.equals(nameToRemove)) {
                result.add(name);
            }
        }

        return result;
    }
}