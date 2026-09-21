package study.inno;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Задача 1
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }


    // Задача 2
    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }


    // Задача 3
    public static boolean isPositive(int n) {
        return n >= 0 ? true : false;
    }


    // Задача 4
    public static String getGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else if (score >= 41 && score <= 60) {
            return "C";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 81 && score <= 100) {
            return "A";
        } else {
            return "Error";
        }
    }


    // Задача 5
    public static String blastOff(int start) {
        String result = "";

        for (int i = start; i >= 1; i--) {
            result += i + " ";
        }

        result += "Поехали!";

        return result;
    }


    // Задача 6
    public static int sumToN(int n) {
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        return sum;
    }


    // Задача 7
    public static boolean hasBug(String[] messages) {
        for (String message : messages) {
            if (message.equalsIgnoreCase("Bug")) {
                return true;
            }
        }

        return false;
    }


    // Задача 8
    public static String getEvenInRange(int start, int end) {
        String result = "";

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {

                if (!result.isEmpty()) {
                    result += " ";
                }

                result += i;
            }
        }

        return result;
    }


    // Задача 9
    public static int findMax(int[] arr) {
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }


    // Задача 10
    public static String[] reverse(String[] arr) {
        String[] result = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - 1 - i];
        }

        return result;
    }


    // Задача 11
    public static double calcAverage(List<Integer> list) {
        int sum = 0;

        for (int number : list) {
            sum += number;
        }

        return (double) sum / list.size();
    }


    // Задача 12
    public static List<String> removeSpecificName(
            List<String> list,
            String nameToRemove) {

        List<String> result = new ArrayList<>();

        for (String name : list) {
            if (!name.equals(nameToRemove)) {
                result.add(name);
            }
        }

        return result;
    }


    // ПРОВЕРКА ВСЕХ МЕТОДОВ
    public static void main(String[] args) {

        // Задача 1
        System.out.println("Задача 1:");
        System.out.println(isEven(4));
        System.out.println(isEven(5));


        // Задача 2
        System.out.println("Задача 2:");
        System.out.println(checkAccess(20));
        System.out.println(checkAccess(18));


        // Задача 3
        System.out.println("Задача 3:");
        System.out.println(isPositive(5));
        System.out.println(isPositive(0));
        System.out.println(isPositive(-5));


        // Задача 4
        System.out.println("Задача 4:");
        System.out.println(getGrade(10));
        System.out.println(getGrade(30));
        System.out.println(getGrade(50));
        System.out.println(getGrade(70));
        System.out.println(getGrade(90));
        System.out.println(getGrade(101));


        // Задача 5
        System.out.println("Задача 5:");
        System.out.println(blastOff(5));


        // Задача 6
        System.out.println("Задача 6:");
        System.out.println(sumToN(5));


        // Задача 7
        System.out.println("Задача 7:");
        String[] messages = {"Hello", "Test", "Bug", "Java"};
        System.out.println(hasBug(messages));


        // Задача 8
        System.out.println("Задача 8:");
        System.out.println(getEvenInRange(2, 10));


        // Задача 9
        System.out.println("Задача 9:");
        int[] numbers = {5, 10, 3, 25, 7};
        System.out.println(findMax(numbers));


        // Задача 10
        System.out.println("Задача 10:");
        String[] words = {"One", "Two", "Zero"};
        String[] reversedWords = reverse(words);

        for (String word : reversedWords) {
            System.out.print(word + " ");
        }

        System.out.println();


        // Задача 11
        System.out.println("Задача 11:");
        List<Integer> numbersList = new ArrayList<>();
        numbersList.add(10);
        numbersList.add(20);
        numbersList.add(30);

        System.out.println(calcAverage(numbersList));


        // Задача 12
        System.out.println("Задача 12:");
        List<String> names = new ArrayList<>();
        names.add("Anna");
        names.add("Ivan");
        names.add("Maria");
        names.add("Ivan");

        System.out.println(
                removeSpecificName(names, "Ivan")
        );
    }
}
