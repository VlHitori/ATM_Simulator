public class Main {
    public static void main(String[] args) throws Exception {
        ATM atm = new ATM();
        if (args.length > 0 && args[0].equals("-admin")) {
            atm.adminMode();
        } else {
        atm.start();
        }
    }
}
