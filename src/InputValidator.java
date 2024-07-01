import java.util.InputMismatchException;
import java.util.Scanner;
public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getValidIntInput() {
        while(true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Pleace enter an integer number.");
                scanner.nextLine();
            }
        }
    }

    public static double getValidDoubleInput() {
        while(true) {
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Pleace enter a number");
                scanner.nextLine();
            }
        }
    }
    public static String getValidStringInput() {
        return scanner.nextLine();
    }
}
