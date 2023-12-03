package com.proyek_amd;

import java.util.ArrayList;

class Person {
    String name;
    String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", address=" + address + "]";
    }
}

class Account {
    String accountNumber;
    double balance;

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [accountNumber=" + accountNumber + ", balance=" + balance + "]";
    }
}

class Customer extends Person {
    Account account;

    public Customer(String name, String address, String accountNumber, double balance) {
        super(name, address);
        this.account = new Account(accountNumber, balance);
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", address=" + address + ", account=" + account + "]";
    }
}

class Transaction {
    Customer sender;
    Customer receiver;
    double amount;

    public Transaction(Customer sender, Customer receiver, double amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction [sender=" + sender + ", receiver=" + receiver + ", amount=" + amount + "]";
    }
}

class Bank {
    ArrayList<Customer> customers;
    TransactionHistory transactionHistory;

    public Bank() {
        this.customers = new ArrayList<>();
        this.transactionHistory = new TransactionHistory();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void makeTransaction(Customer sender, Customer receiver, double amount) {
        Transaction transaction = new Transaction(sender, receiver, amount);
        transactionHistory.addTransaction(transaction);
    }
}

class Admin extends Person {
    public Admin(String name, String address) {
        super(name, address);
    }

    public void viewAllCustomers(Bank bank) {
        for (Customer customer : bank.customers) {
            System.out.println("Customer: " + customer.name + ", Address: " + customer.address);
        }
    }
}

class RegularUser extends Customer {
    public RegularUser(String name, String address, String accountNumber, double balance) {
        super(name, address, accountNumber, balance);
    }

    public void viewOwnTransactionHistory(Bank bank) {
        bank.transactionHistory.viewAllTransactions();
    }
}

class BankAdmin extends Admin {
    public BankAdmin(String name, String address) {
        super(name, address);
    }

    public void modifyCustomerBalance(Customer customer, double newBalance) {
        customer.account.balance = newBalance;
    }
}

class TransactionHistory {
    ArrayList<Transaction> transactionHistory;

    public TransactionHistory() {
        this.transactionHistory = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public void viewAllTransactions() {
        for (Transaction transaction : transactionHistory) {
            System.out.println("Transaction: " + transaction.amount + " from " + transaction.sender.name + " to "
                    + transaction.receiver.name);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Account account1 = new Account("12345", 1000);
        Customer customer1 = new Customer("John Doe", "123 Main St", account1.accountNumber, account1.balance);

        Account account2 = new Account("67890", 500);
        Customer customer2 = new Customer("Jane Doe", "456 Oak St", account2.accountNumber, account2.balance);

        Admin admin = new Admin("Admin", "Bank HQ");
        BankAdmin bankAdmin = new BankAdmin("BankAdmin", "Bank HQ");

        Bank bank = new Bank();
        bank.addCustomer(customer1);
        bank.addCustomer(customer2);

        admin.viewAllCustomers(bank);

        RegularUser user1 = new RegularUser("Alice", "789 Pine St", account1.accountNumber, account1.balance);
        RegularUser user2 = new RegularUser("Bob", "101 Elm St", account2.accountNumber, account2.balance);

        user1.viewOwnTransactionHistory(bank);

        bank.makeTransaction(user1, user2, 200);

        user1.viewOwnTransactionHistory(bank);

        // Example of Bank Admin modifying customer balance
        bankAdmin.modifyCustomerBalance(customer1, 1500);
        System.out.println("New balance for customer1: " + customer1.account.balance);
    }
}