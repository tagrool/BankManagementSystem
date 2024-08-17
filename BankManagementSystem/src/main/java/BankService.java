import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BankService {
    private Map<Integer, User> users = new HashMap<>();
    private UserAccountRepository userAccountRepository;

    public BankService(String dbUrl, String dbUser, String dbPassword) {
        try {
            this.userAccountRepository = new UserAccountRepository(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(User user) {
        try {
            userAccountRepository.saveUser(user);
            users.put(user.getUserId(), user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User authenticateUser(String userName, String password) {
        try {
            return userAccountRepository.authenticateUser(userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deposit(int userId, int accountNumber, double amount) {
        try {
            Account account = userAccountRepository.getAccount(userId, accountNumber);
            if (account != null) {
                account.setBalance(account.getBalance() + amount);
                userAccountRepository.updateAccountBalance(account);
            } else {
                System.out.println("Hesab tapılmadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void withdraw(int userId, int accountNumber, double amount) {
        try {
            Account account = userAccountRepository.getAccount(userId, accountNumber);
            if (account != null) {
                if (account.getBalance() >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    userAccountRepository.updateAccountBalance(account);
                } else {
                    System.out.println("Kifayət qədər balans yoxdur.");
                }
            } else {
                System.out.println("Hesab tapılmadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void transfer(int userId, int fromAccountNumber, int toAccountNumber, double amount) {
        try {
            Account fromAccount = userAccountRepository.getAccount(userId, fromAccountNumber);
            Account toAccount = userAccountRepository.getAccount(userId, toAccountNumber);
            if (fromAccount != null && toAccount != null) {
                if (fromAccount.getBalance() >= amount) {
                    fromAccount.setBalance(fromAccount.getBalance() - amount);
                    toAccount.setBalance(toAccount.getBalance() + amount);
                    userAccountRepository.updateAccountBalance(fromAccount);
                    userAccountRepository.updateAccountBalance(toAccount);
                } else {
                    System.out.println("Kifayət qədər balans yoxdur.");
                }
            } else {
                System.out.println("Hesablardan biri tapılmadı.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveAccount(Account account) {
        try {
            userAccountRepository.saveAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        userAccountRepository.close();
    }
}
