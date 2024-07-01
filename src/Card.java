import java.time.LocalDateTime;

public class Card {
    private String cardNumber;
    private String pinCode;
    private BankAccount account;
    private int pinAttempts;
    private LocalDateTime blockedUntil;

    public Card(String cardNumber, String pinCode, BankAccount account) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.account = account;
        this.pinAttempts = 0;
        this.blockedUntil = null;
    }

    public boolean validatePin(String pin) {
        if(isBlocked()) {
            throw new IllegalStateException("Card is blocked until" + getBlockedUntil());
        }
        if (this.pinCode.equals(pin)) {
            pinAttempts = 0;
            return true;
        } else {
            pinAttempts ++;
            if (pinAttempts >= 3) {
                block();
            }
            return false;
        }
    }

    public boolean isBlocked() {
        return blockedUntil != null && LocalDateTime.now().isBefore(blockedUntil);
    }

    public void block() {
        this.blockedUntil = LocalDateTime.now().plusDays(1);
    }

    public void unblock() {
        this.blockedUntil = null;
        this.pinAttempts = 0;
    }

    public LocalDateTime getBlockedUntil() {
        return blockedUntil;
    }
    
    public int getPinAttempts() {
        return pinAttempts;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setPinAttemps(int pinAttempts) {
        this.pinAttempts = pinAttempts;
    }

    public void setBlockedUntil(LocalDateTime blockedUntil) {
        this.blockedUntil = blockedUntil;
    }
}
