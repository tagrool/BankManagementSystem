import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int sourceAccount;
    private int targetAccount;
    private double amount;
    private LocalDateTime date;
    private String type;

    public Transaction(int transactionId, int sourceAccount, int targetAccount, double amount, LocalDateTime date, String type) {
        this.transactionId = transactionId;
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getSourceAccount() {
        return sourceAccount;
    }

    public int getTargetAccount() {
        return targetAccount;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void displayTransactionInfo() {
        System.out.println("Əməliyyat ID: " + transactionId);
        System.out.println("Mənbə Hesab: " + sourceAccount);
        System.out.println("Hədəf Hesab: " + targetAccount);
        System.out.println("Miqdar: " + amount);
        System.out.println("Tarix: " + date);
        System.out.println("Tip: " + type);
    }
}
