import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionRepository {
    private Connection connection;

    public TransactionRepository(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    public void save(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_id, source_account, target_account, amount, date, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getTransactionId());
            stmt.setInt(2, transaction.getSourceAccount());
            stmt.setInt(3, transaction.getTargetAccount());
            stmt.setDouble(4, transaction.getAmount());
            stmt.setString(5, formatDateTime(transaction.getDate()));
            stmt.setString(6, transaction.getType());
            stmt.executeUpdate();
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
