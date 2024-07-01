import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

public class DataHandler {
    private static final String FILE_NAME = "accounts.txt";
    public static Map<String, Card> loadCards() {
        Map<String, Card> cards = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length < 5) continue;
                String cardNumber = parts[0];
                String pinCode = parts[1];
                int pinAttempts = Integer.parseInt(parts[2]);
                double balance = Double.parseDouble(parts[3]);
                LocalDateTime blockedUntil = parts[4].equals("null") ? null : LocalDateTime.parse(parts[4]);

                BankAccount account = new BankAccount(cardNumber, balance);
                Card card = new Card(cardNumber, pinCode, account);
                card.setPinAttemps(pinAttempts);
                card.setBlockedUntil(blockedUntil);
            
                cards.put(cardNumber, card);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return cards; 
    }

    public static void saveCards(Map<String, Card> cards) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Card card: cards.values()) {
                String blockedUntilStr = card.getBlockedUntil() == null ? "null" : card.getBlockedUntil().toString();
                bw.write(card.getCardNumber() + " " + 
                    card.getPinCode() + " " +
                    card.getPinAttempts() + " " +
                    card.getAccount().getBalance() + " " + 
                    blockedUntilStr);
                bw.newLine();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}