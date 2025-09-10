package com.ayankoley.splitwise.repository;

import com.ayankoley.splitwise.model.Group;
import com.ayankoley.splitwise.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findTransactionByGroup(Group group);
}
