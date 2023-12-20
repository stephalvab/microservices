package com.devsu.msaccount.repository;

import com.devsu.msaccount.model.dto.AccountStatementDto;
import com.devsu.msaccount.model.entity.Client;
import com.devsu.msaccount.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

//    @Query("SELECT  new com.devsu.msaccount.model.dto.AccountStatementDto( " +
//            "t.dateRegister, c.name, a.accountNumber, ac.name, " +
//            "t.status, t.amount, t.availableBalance) " +
//            "FROM Transaction t " +
//            "JOIN Account a ON a.accountId = t.account.accountId " +
//            "JOIN AccountType ac ON ac.accountTypeId = a.accountType.accountTypeId " +
//            "JOIN Person c ON c.clientId = a.client.clientId " +
//            "WHERE c.clientId= :client AND t.dateRegister BETWEEN :startDate AND :endDate")
//    List<AccountStatementDto> accountStatementBetweenDatesAndClient(
//            @Param("client") int client,
//            @Param("startDate") Date startDate,
//            @Param("endDate") Date endDate);

    @Query("SELECT t FROM Transaction t " +
            "JOIN FETCH t.account " +
            "JOIN FETCH t.account.client " +
            "JOIN FETCH t.account.accountType " +
            "WHERE t.account.client.clientId = :clientId " +
            "AND t.dateRegister BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByClientAndDateRange(
            @Param("clientId") int clientId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

}
