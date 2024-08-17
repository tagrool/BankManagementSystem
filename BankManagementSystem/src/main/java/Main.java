import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/bankdb";
        String dbUser = "root";
        String dbPassword = "******";

        BankService bankService = new BankService(dbUrl, dbUser, dbPassword);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Xoşgəldiniz, seçim edin:");
        System.out.println("1. Giriş edin");
        System.out.println("2. Qeydiyyatdan keçin");

        int choice = scanner.nextInt();
        scanner.nextLine();


        if (choice == 1) {
            System.out.print("İstifadəçi adı: ");
            String userName = scanner.nextLine();
            System.out.print("Şifrə: ");
            String password = scanner.nextLine();

            User user = bankService.authenticateUser(userName, password);

            if (user != null) {
                System.out.println("Giriş başarılı!");
                showMenu(bankService, user);
            } else {
                System.out.println("Geçersiz kullanıcı adı veya şifre.");
            }
        } else if (choice == 2) {
            System.out.print("İstifadəçi adı: ");
            String userName = scanner.nextLine();
            System.out.print("Şifrə: ");
            String password = scanner.nextLine();

            User newUser = new User(0, userName, password);
            bankService.registerUser(newUser);

            System.out.println("Qeydiyyat uğurla həyata keçirildi. İndi giriş edə bilərsiniz...");
        } else {
            System.out.println("Mümkün olmayan seçim.");
        }

        bankService.close();
    }

    private static void showMenu(BankService bankService, User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Hesab əlavə edin");
            System.out.println("2. Hesabınızı artırın");
            System.out.println("3. Hesabdan çıxarış edin");
            System.out.println("4. Hesablar arasında transfer edin");
            System.out.println("5. Çıxış");
            System.out.print("Seçiminizi edin: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Hesab Tipi (Checking/Savings/Deposit/Business): ");
                    String accountType = scanner.nextLine();
                    Account account = new Account(0, 0, accountType, user.getUserId());
                    user.addAccount(account);
                    bankService.saveAccount(account);
                    System.out.println("Hesab uğurla əlavə edildi...");
                }
                case 2 -> {
                    System.out.print("Hesab No: ");
                    int accountNumber = scanner.nextInt();
                    System.out.print("Yatırılacaq Miqdar: ");
                    double amount = scanner.nextDouble();
                    bankService.deposit(user.getUserId(), accountNumber, amount);
                    System.out.println("Məbləğ uğurla əlavə edildi....");
                }
                case 3 -> {
                    System.out.print("Hesab No: ");
                    int accountNumber = scanner.nextInt();
                    System.out.print("Çıxarış ediləcəl məbləğ: ");
                    double amount = scanner.nextDouble();
                    bankService.withdraw(user.getUserId(), accountNumber, amount);
                    System.out.println("Məbləğ uğurla əlavə edildi..");
                }
                case 4 -> {
                    System.out.print("Mənbə Hesab No: ");
                    int fromAccount = scanner.nextInt();
                    System.out.print("Hədəf Hesap No: ");
                    int toAccount = scanner.nextInt();
                    System.out.print("Transfer Miqdarı: ");
                    double amount = scanner.nextDouble();
                    bankService.transfer(user.getUserId(), fromAccount, toAccount, amount);
                    System.out.println("Transfer uğurlu oldu..");
                }
                case 5 -> {
                    System.out.println("Çıxış edilir...");
                    return;
                }
                default -> System.out.println("Düzgün seçim edilmədi, yenidən cəhd edin..");
            }
        }
    }
}
