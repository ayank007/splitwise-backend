package com.ayankoley.splitwise.controller;

import com.ayankoley.splitwise.dto.CreateExpenseReqDTO;
import com.ayankoley.splitwise.dto.CreateGroupReqDTO;
import com.ayankoley.splitwise.dto.CreateGroupResDTO;
import com.ayankoley.splitwise.model.Expense;
import com.ayankoley.splitwise.model.Group;
import com.ayankoley.splitwise.model.Transaction;
import com.ayankoley.splitwise.model.User;
import com.ayankoley.splitwise.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/group")
    public ResponseEntity<CreateGroupResDTO> createGroup(@RequestBody CreateGroupReqDTO createGroupReqDTO) {
        Group newGroup = groupService.createGroup(createGroupReqDTO);

        CreateGroupResDTO groupResDTO = new CreateGroupResDTO();
        groupResDTO.setGroupName(newGroup.getName());
        groupResDTO.setGroupId(newGroup.getId());
        groupResDTO.setAdminName(newGroup.getAdmin().getName());
        // getAdmin() returning a User object and getName() returning String Name as User object has name variable

        List<String> members = new ArrayList<>();
        for(User member : newGroup.getMembers()) {
            members.add(member.getName());
        }
        groupResDTO.setMembers(members);

        return ResponseEntity.ok(groupResDTO);
    }


    @GetMapping("/group/{groupId}")
    public ResponseEntity<Group> getAllGroups(
            @PathVariable("groupId") int groupId
    ) {
        Group group = groupService.showGroup(groupId);

        return ResponseEntity.ok(group);
    }

    @PostMapping("/group/expense/{id}")
    public ResponseEntity<Expense> createExpense(
            @PathVariable("id") int groupId,
            @RequestBody CreateExpenseReqDTO reqDTO
    ) {
        Expense expense = groupService.createExpense(groupId, reqDTO);
        if (expense != null) {
            return ResponseEntity.ok(expense);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/group/expense/{id}")
    public ResponseEntity<Expense> getExpense(
            @PathVariable("id") int expenseId
    ) {
        Expense expense = groupService.showExpense(expenseId);

        return ResponseEntity.ok(expense);
    }

    @GetMapping("/group/settle/{id}")
    public ResponseEntity<List<Transaction>> settleExpense(
            @PathVariable("id") int groupId
    ) {
        List<Transaction> transactions = groupService.settleExpense(groupId);

        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/group/transaction/{id}")
    public ResponseEntity<List<Transaction>> getTransaction(
            @PathVariable("id") int groupId
    ) {
        List<Transaction> transactions  = groupService.showTransaction(groupId);

        return ResponseEntity.ok(transactions);
    }
}
