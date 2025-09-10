package com.ayankoley.splitwise.service;

import com.ayankoley.splitwise.dto.CreateExpenseReqDTO;
import com.ayankoley.splitwise.dto.CreateGroupReqDTO;
import com.ayankoley.splitwise.dto.UserExpenseReqDTO;
import com.ayankoley.splitwise.model.*;
import com.ayankoley.splitwise.repository.ExpenseRepository;
import com.ayankoley.splitwise.repository.GroupRepository;
import com.ayankoley.splitwise.repository.TransactionRepository;
import com.ayankoley.splitwise.repository.UserExpenseRepository;
import com.ayankoley.splitwise.service.strategy.SettlementStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository repo;

    @Autowired
    private ExpenseRepository expenseRepo;

    @Autowired
    private UserExpenseRepository userExpenseRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    public Group findGroupById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Group createGroup(CreateGroupReqDTO groupReqDTO) {
        Group group = new Group();
        group.setName(groupReqDTO.getName());

        int adminId = groupReqDTO.getAdminId();
        User admin = userService.findById(adminId);
        group.setAdmin(admin);

        List<Integer> memberIds = groupReqDTO.getMemberIds();
        List<User> members = new ArrayList<>();
        for (Integer memberId : memberIds) {
            User user = userService.findById(memberId);
            members.add(user);
        }
        group.setMembers(members);

        return repo.save(group);
    }

    public Expense createExpense(int groupId, CreateExpenseReqDTO reqDTO) {
        Expense expense = new Expense();

//        Converting DTO to Entity
        expense.setDescription(reqDTO.getDescription());
        expense.setAmount(reqDTO.getAmount());
        expense.setCreatedOn(LocalDateTime.now());

        User user = userService.findById(reqDTO.getCreatedById());
        expense.setCreatedBy(user);

        List<UserExpense> userExpenses = new ArrayList<>();
        List<UserExpenseReqDTO> userExpenseReqs = reqDTO.getUserExpenses();
        for(UserExpenseReqDTO userExpenseReqDTO : userExpenseReqs) {
            UserExpense userExpense = new UserExpense();
            User expenseUser = userService.findById(userExpenseReqDTO.getUserId());
            userExpense.setUser(expenseUser);

            userExpense.setAmount(userExpenseReqDTO.getAmount());

            int expenseType = userExpenseReqDTO.getExpenseType();
            if(expenseType == 0) {
                userExpense.setType(UserExpenseType.PAID);
            } else {
                userExpense.setType(UserExpenseType.HAD_TO_PAY);
            }

            userExpenseRepo.save(userExpense);

            userExpenses.add(userExpense);
        }

        expense.setEntries(userExpenses);

//        Database Call
        Expense newExpense = expenseRepo.save(expense);

//        Updating Group for expense mapping
        Group group = findGroupById(groupId);
        group.getExpenses().add(newExpense);
        repo.save(group);

        return newExpense;
    }

    public List<Transaction> settleExpense(int groupId) {
        Group group = findGroupById(groupId);
        SettlementStrategy settlementStrategy = new SettlementStrategy();
        List<Transaction> transactions = settlementStrategy.settle(group);

        transactionRepo.saveAll(transactions);

        return transactions;
    }

//    Just for testing (to be deleted)...
    public Group showGroup(int groupId) {
        return repo.findById(groupId).orElse(null);
    }
    public Expense showExpense(int expenseId) {
        return expenseRepo.findById(expenseId).orElse(null);
    }

    public List<Transaction> showTransaction(int groupId) {
        Group group = findGroupById(groupId);
        List<Transaction> transactions = transactionRepo.findTransactionByGroup(group);
        return transactions;
    }
}
