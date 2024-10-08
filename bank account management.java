import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

// Class representing a BankAccount
class Account {
    private static final AtomicInteger accountIdGenerator = new AtomicInteger(1000);
    private final int accountId;
    private final String accountHolder;
    private double currentBalance;

    public Account(String accountHolder) {
        this.accountHolder = accountHolder;
        this.accountId = accountIdGenerator.getAndIncrement();
        this.currentBalance = 0.0;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void depositFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount should be greater than zero.");
        }
        currentBalance += amount;
        System.out.println("Successfully deposited: " + amount + ", New Balance: " + currentBalance);
    }

    public void withdrawFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount should be positive.");
        }
        if (amount > currentBalance) {
            throw new IllegalArgumentException("Insufficient balance. Current balance: " + currentBalance);
        }
        currentBalance -= amount;
        System.out.println("Successfully withdrew: " + amount + ", New Balance: " + currentBalance);
    }
}

// Class representing the Bank system
class BankingSystem {
    private final Map<Integer, Account> accountMap = new HashMap<>();

    public Account openAccount(String holderName) {
        Account newAccount = new Account(holderName);
        accountMap.put(newAccount.getAccountId(), newAccount);
        System.out.println("Account opened for " + holderName + " with Account ID: " + newAccount.getAccountId());
        return newAccount;
    }

    public Account findAccount(int accountId) {
        return accountMap.get(accountId);
    }

    public void listAllAccounts() {
        System.out.println("Listing all Accounts:");
        for (Account acc : accountMap.values()) {
            System.out.println("Account ID: " + acc.getAccountId() +
                               ", Holder: " + acc.getAccountHolder() +
                               ", Balance: " + acc.getCurrentBalance());
        }
    }
}

// Main class to drive the application
public class BankingApplication {
    public static void main(String[] args) {
        BankingSystem bankSystem = new BankingSystem();
        Scanner inputScanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Open Account\n2. Deposit\n3. Withdraw\n4. Check Balance\n5. Show All Accounts\n6. Exit");
            System.out.print("Select an option: ");
            int choice = inputScanner.nextInt();
            inputScanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the account holder's name: ");
                    String accountHolderName = inputScanner.nextLine();
                    bankSystem.openAccount(accountHolderName);
                    break;
                case 2:
                    System.out.print("Enter account ID: ");
                    int depositAccountId = inputScanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = inputScanner.nextDouble();
                    try {
                        Account depositAcc = bankSystem.findAccount(depositAccountId);
                        if (depositAcc != null) {
                            depositAcc.depositFunds(depositAmount);
                        } else {
                            System.out.println("Account not found.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter account ID: ");
                    int withdrawalAccountId = inputScanner.nextInt();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = inputScanner.nextDouble();
                    try {
                        Account withdrawalAcc = bankSystem.findAccount(withdrawalAccountId);
                        if (withdrawalAcc != null) {
                            withdrawalAcc.withdrawFunds(withdrawalAmount);
                        } else {
                            System.out.println("Account not found.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("Enter account ID: ");
                    int balanceAccountId = inputScanner.nextInt();
                    Account balanceAcc = bankSystem.findAccount(balanceAccountId);
                    if (balanceAcc != null) {
                        System.out.println("Current Balance: " + balanceAcc.getCurrentBalance());
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 5:
                    bankSystem.listAllAccounts();
                    break;
                case 6:
                    System.out.println("Exiting application...");
                    inputScanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
