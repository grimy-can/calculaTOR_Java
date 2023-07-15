import java.util.*;

/**
 * Kata Academy * Тестовое задание Калькулятор_Java
 * @version 0.1
 * @author Рязанов Вадим
 */

public class Main {

    static int a;
    static int b;
    static String action;

    public static void main(String[] args) throws TestExceptions {

        Scanner scan = new Scanner((System.in));
        String userInput = scan.nextLine();  //get user input

        int [] arabic = {1,2,3,4,5,6,7,8,9,10};                                  // арабский набор цифр
        String [] roman = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};  // римский набор цифр
        String [] actions = {"-", "+", "*", "/"};                                // допустимые операторы
        String [] severance;

        try {
            // разбивка ввода на 2 операнда и оператор :
            severance = userInput.split(" ");
            if ( severance.length != 3 ) {
                throw new TestExceptions("Формат ввода не удовлетворяет заданию");
            } else {

                action = severance[1];  // оператор
                String aChar = severance[0];    // операнд 1
                String bChar = severance[2];    // операнд 2

                // поиск оператора:
                if (Arrays.asList(actions).contains(action)) {

                    // поиск римских операндов:
                    if (Arrays.asList(roman).contains(aChar) &&
                            Arrays.asList(roman).contains(bChar)) {
                        // поиск римских цифр:
                        int aIndex = Arrays.asList(roman).indexOf(aChar);
                        int bIndex = Arrays.asList(roman).indexOf(bChar);

                        a = arabic[aIndex];
                        b = arabic[bIndex];
                        // математика по-римски:
                        doTheRomanMath();

                    } else {

                        // поиск арабских операндов:
                        try {
                            // парс арабских цифр:
                            a = Integer.parseInt(severance[0]);
                            b = Integer.parseInt(severance[2]);
                            // проверка условия " от 1 до 10 ":
                            if (Arrays.stream(arabic).anyMatch(x -> x == a) &&
                                    Arrays.stream(arabic).anyMatch(x -> x == b)) {
                                // математика по-арабски:
                                doTheArabicMath();
                            } else {
                                throw new TestExceptions("Формат ввода не удовлетворяет заданию");
                            }

                        } catch (InputMismatchException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else  {
                    throw new TestExceptions("Не найдена математическая операция");
                }
            }

        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    static void doTheArabicMath() {

        switch (action) {
            case "-" -> System.out.println(a - b);
            case "+" -> System.out.println(a + b);
            case "*" -> System.out.println(a * b);
            case "/" -> System.out.println(a / b);
        }
    }



    static void doTheRomanMath() throws TestExceptions {
        
        int arabResult = 0;
        
        switch (action) {
            case "-" -> arabResult = a - b;
            case "+" -> arabResult = a + b;
            case "*" -> arabResult = a * b;
            case "/" -> arabResult = a / b;
        }

        if (arabResult > 0 ) {

            String romanResult = convertArabResultToRoman(arabResult);
            System.out.println(romanResult);

        } else {
            if (arabResult == 0) {
                throw new TestExceptions("Римская система не использует ноль");
            } else {
                throw new TestExceptions("Римская система не использует отрицательные числа");
            }
        }
    }

    static String convertArabResultToRoman(int result)  {

        StringBuilder romanString = new StringBuilder();

        if (result == RomeDigit.C.arabDigit) {
            romanString.append(RomeDigit.C.name());  // result == 100
        } else if (result == RomeDigit.XC.arabDigit) {
            romanString.append(RomeDigit.XC.name());    // result == 90
        } else {

            // римский порядок: {полтинники, десятки, пятёрки} :
            int [] convertOrder = {0, 0, 0};
            // римские единицы:
            int ones;

            convertOrder[0] = result / RomeDigit.L.arabDigit;   // количество полтинников

            if (convertOrder[0] > 0) {
                result = result - convertOrder[0] * RomeDigit.L.arabDigit; // вычет полтинников
            }

            convertOrder[1] = result / RomeDigit.X.arabDigit; // количество десяток
            if (convertOrder[1] > 0) {
                result = result - convertOrder[1] * RomeDigit.X.arabDigit; // вычет десяток
            }

            convertOrder[2] = result / RomeDigit.V.arabDigit;   // количество пятёрок
            if (convertOrder[2] > 0) {
                result = result - convertOrder[2] * RomeDigit.V.arabDigit; // вычет пятёрок
            }

            ones = result / RomeDigit.I.arabDigit;   // количество единиц


            RomeDigit [] romeSystem = {
                    RomeDigit.L,
                    RomeDigit.X,
                    RomeDigit.V,
                    RomeDigit.I};

            int counter = -1;
            for (int x:convertOrder) {
                counter += 1;
                if (x > 0) {
                    if (x <= 3) {
                        romanString.append(romeSystem[counter].name().repeat(x));
                    } else {
                        romanString.append(romeSystem[counter].name());
                        romanString.append(romeSystem[counter - 1].name());
                    }
                }
            }

            switch (ones) {
                case 1 -> romanString.append(RomeDigit.I.name());
                case 2 -> romanString.append(RomeDigit.II.name());
                case 3 -> romanString.append(RomeDigit.III.name());
                case 4 -> romanString.append(RomeDigit.IV.name());
                case 6 -> romanString.append(RomeDigit.VI.name());
                case 7 -> romanString.append(RomeDigit.VII.name());
                case 8 -> romanString.append(RomeDigit.VIII.name());
                case 9 -> romanString.append(RomeDigit.IX.name());
            }
        }

        return romanString.toString();
    }
}
