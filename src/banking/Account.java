package banking;

public class Account {

    private static String cardNumber;
    private static String pin;
    private static int balance;
    String numberOfCard = "400000" + String.format("%09d",
            (long) (Math.random() * 999999999L));

    public Account() {
        cardNumber = numberOfCard + generateCheckDigit();

        pin = String.format("%04d", (long) (Math.random() * 9999));
        balance = 0;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        Account.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        Account.pin = pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        Account.balance = balance;
    }

    private int generateCheckDigit() {
        int sum = 0;
        int remainder = (numberOfCard.length() + 1) % 2;
        for (int i = 0; i < numberOfCard.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(numberOfCard.substring(i, (i + 1)));

            if ((i % 2) == remainder) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        // The check digit is the number required to make the sum a multiple of 10
        int mod = sum % 10;
        int checkDigit = ((mod == 0) ? 0 : 10 - mod);

        return checkDigit;
    }

}