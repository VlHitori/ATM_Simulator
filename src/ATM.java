import java.util.HashMap;
import java.util.Map;

public class ATM {
    private static Map<String, Card> cards = new HashMap<>();
    private static final int ATM_FOUNDS_LIMIT = 50_000;
    
    public void start() {
        cards = DataHandler.loadCards();
        while(true) {
            System.out.println("Enter your card num or 'quit' (format: XXXX-XXXX-XXXX-XXXX): ");
            String cardNumber = InputValidator.getValidStringInput();
            if (cardNumber.equalsIgnoreCase("quit")) {
                break;
            }
            Card card = cards.get(cardNumber);
            if (card == null) {
                System.out.println("Card not found.");
                continue;
            }
            if(card.isBlocked()) {
                System.out.println("This card is blocked until " + card.getBlockedUntil());
                continue;
            }
            if(!authenticate(card)) {
                card.block();
                cards.put(cardNumber, card);
                DataHandler.saveCards(cards);
                continue;
            }
            card.unblock();
            BankAccount account = card.getAccount();
            performOperations(account);
            DataHandler.saveCards(cards);
        }
    }

    private boolean authenticate(Card card) {
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("Enter your PIN code: ");
                String pin = InputValidator.getValidStringInput();
                if (card.validatePin(pin)) {
                    return true;
                } else {
                    System.out.println("Invalid PIN. Try again.");
                }
            } 
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("Card is blocked for 24 hours.");
        return false;
    }

    private void performOperations(BankAccount account) {
        while (true) {
            System.out.println("Choose an operation:\n 1 - Cheeck Balance \n 2 - Withdraw\n 3 - Deposit \n 4 - Exit");
            int choice = InputValidator.getValidIntInput();
                
            switch(choice) {
                case 1: 
                    System.out.println("Current Balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.println("Enter amount of withdraw: ");
                    double amount = InputValidator.getValidDoubleInput();
                    
                    if (amount <= ATM_FOUNDS_LIMIT && amount <= account.getBalance()) {
                        account.withdraw(amount);
                        System.out.println("Withdraw successful. New Balance: " + account.getBalance());
                    } else {
                        System.out.println("Withdrawal amount exceeds balance or ATM limit.");
                    }
                    break;
                case 3:
                    System.out.println("Enter amount to deposit: ");
                    double depositAmount = InputValidator.getValidDoubleInput();
                    
                    if (depositAmount <= 1_000_000) {
                        account.deposit(depositAmount);
                        System.out.println("Deposit successful. New Balance: " + account.getBalance());
                    } else {
                        System.out.println("Deposit amount exceeds limit.");
                    }
                    break;
                case 4: 
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void adminMode() {
        cards = DataHandler.loadCards();
        while(true) {
            System.out.println("Admin mode: \n 1 - Add new card \n 2 - Unblock card \n 3 - Delete card \n 4 - Exit admin mode \n Choose an option: ");
            int option = InputValidator.getValidIntInput();
            switch(option) {
                case 1:
                    addNewCard();
                    break;
                case 2:
                    unblockCard();
                    break;
                case 3:
                    deleteCard();
                    break;
                case 4: 
                    DataHandler.saveCards(cards);
                    System.out.println("Exiting admin mode.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");    
            }
        }
    }

    private void addNewCard() {
        System.out.println("Enter new card number (format: XXXX-XXXX-XXXX-XXXX)");
        String cardNumber = InputValidator.getValidStringInput();
        if (cards.containsKey(cardNumber)) {
            System.out.println("Card number already exists.");
            return;
        }

        System.out.println("Enter new PIN code: ");
        String pinCode = InputValidator.getValidStringInput();

        System.out.println("Enter initial balance: ");
        double balance = InputValidator.getValidDoubleInput();
        BankAccount newAccount = new BankAccount(cardNumber, balance);
        Card newCard = new Card(cardNumber, pinCode, newAccount);
        cards.put(cardNumber, newCard);
        DataHandler.saveCards(cards);
        System.out.println("New card added successfully");
    }

    private void unblockCard() {
        cards = DataHandler.loadCards();
        System.out.println("Enter number card");
        String cardNumber = InputValidator.getValidStringInput();
        Card card = cards.get(cardNumber);

        if (card == null) {
            System.out.println("Card not found");
            return;
        }
        if (!card.isBlocked()) {
            System.out.println("The card is unlock");
            return;
        }

        card.unblock();
        System.out.println("The card is unblocked");
        DataHandler.saveCards(cards);
    }

    private void deleteCard() {
        System.out.println("Enter new card number (format: XXXX-XXXX-XXXX-XXXX)");
        String cardNumber = InputValidator.getValidStringInput();
        if (cards.containsKey(cardNumber)) {
            cards.remove(cardNumber);
            System.out.println("The card has been deleted");
            return;
        } else {
        System.out.println("Card not found");
        return;
        }
    }
}