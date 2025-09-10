package com.ayankoley.splitwise.service.strategy;

import com.ayankoley.splitwise.dto.UserExpenseEntry;
import com.ayankoley.splitwise.model.*;

import java.util.*;

public class SettlementStrategy {
    private final PriorityQueue<UserExpenseEntry> borrowersMinHeap;
    private final PriorityQueue<UserExpenseEntry> lendersMaxHeap;
    private final HashMap<User, Double> userAmountMap;

    public SettlementStrategy() {
        borrowersMinHeap = new PriorityQueue<>(Comparator.comparingDouble(UserExpenseEntry::getAmount));
        lendersMaxHeap = new PriorityQueue<>(Comparator.comparingDouble(UserExpenseEntry::getAmount).reversed());

        userAmountMap = new HashMap<>();
    }

    public List<Transaction> settle(Group group) {
        List<Expense> expenses = group.getExpenses();

//        Creating User -> Amount Map
        for(Expense expense : expenses) {
            List<UserExpense> userExpenses = expense.getEntries();
            for (UserExpense userExpense : userExpenses) {
                User user = userExpense.getUser();
                double amount = userExpense.getAmount();
                if (userExpense.getType().equals(UserExpenseType.HAD_TO_PAY)) {
                    amount *= (-1);
                }
                double mapAmount = userAmountMap.getOrDefault(user, 0.00);
                userAmountMap.put(user, mapAmount + amount);
            }
        }

        System.out.println(Arrays.asList(userAmountMap));
        System.out.println("...Hello...");
        System.out.println(userAmountMap);


//        creating Heaps
        for (Map.Entry<User, Double> entry : userAmountMap.entrySet()) {
            if (entry.getValue() < 0) {
                borrowersMinHeap.add(new UserExpenseEntry(entry.getKey(), entry.getValue()));
            } else if (entry.getValue() > 0) {
                lendersMaxHeap.add(new UserExpenseEntry(entry.getKey(), entry.getValue()));
            }
        }

        System.out.println("Borrowers...");
        System.out.println(borrowersMinHeap);
        System.out.println("Lenders...");
        System.out.println(lendersMaxHeap);

        return settleHeap(borrowersMinHeap, lendersMaxHeap, group);
    }

    private List<Transaction> settleHeap(
            PriorityQueue<UserExpenseEntry> borrowersMinHeap,
            PriorityQueue<UserExpenseEntry> lendersMaxHeap,
            Group group
    ) {
        List<Transaction> transactions = new ArrayList<>();
        while (!borrowersMinHeap.isEmpty()) {
            UserExpenseEntry borrower = borrowersMinHeap.poll();
            UserExpenseEntry lender = lendersMaxHeap.poll();

            assert lender != null;
            double amount = borrower.getAmount() + lender.getAmount();
            Transaction transaction = new Transaction();
            transaction.setSender(borrower.getUser());
            transaction.setReceiver(lender.getUser());
            transaction.setGroup(group);


            if (amount < 0) {
                borrowersMinHeap.add(new UserExpenseEntry(borrower.getUser(), amount));
                transaction.setAmount(lender.getAmount());
            } else if (amount > 0) {
                lendersMaxHeap.add(new UserExpenseEntry(lender.getUser(), amount));
                transaction.setAmount(Math.abs(borrower.getAmount()));
            } else {
                transaction.setAmount(lender.getAmount());
            }

            transactions.add(transaction);
        }

        return transactions;
    }
}
